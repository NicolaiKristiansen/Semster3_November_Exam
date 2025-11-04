package app.dao;

import app.Category;
import app.dto.CandidateDTO;
import app.dto.EnrichedSkillDTO;
import app.dto.SkillDTO;
import app.entity.Candidate;
import app.entity.Skill;
import app.exceptions.EntityNotFoundException;
import app.utils.externalAPI.Data;
import app.utils.externalAPI.SkillStatsAPI;
import app.utils.externalAPI.Stats;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class CandidateDAO implements IDAO<Candidate, CandidateDTO> {
    private final EntityManagerFactory emf;

    public CandidateDAO(EntityManagerFactory emf){

        this.emf = emf;
    }

    @Override
    public Candidate create(CandidateDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Candidate candidate = new Candidate(dto);
            em.persist(candidate);
            em.getTransaction().commit();
            return candidate;
        }
    }

    @Override
    public Candidate findById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            Candidate candidate = em.find(Candidate.class, id);
            if(candidate == null){
                System.out.println("Couldn't find the candidate with id:" + id);
                return null;
            }
            return candidate;
        }
    }

    @Override
    public Candidate update(CandidateDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Candidate candidate = em.find(Candidate.class, dto.getId());

            if(candidate == null){
                System.out.println("Could not find the candidate to be updated");
                em.getTransaction().rollback();
                return null;
            }

            if(dto.getName() !=null){
                candidate.setName(dto.getName());
            }
            if(dto.getPhone() !=null){
                candidate.setPhone(dto.getPhone());
            }
            if(dto.getEducationBackground() !=null){
                candidate.setEducationBackground(dto.getEducationBackground());
            }
            if(dto.getSkills() !=null){
                candidate.setSkills(dto.getSkills());
            }

            em.getTransaction().commit();
            return candidate;
        }
    }

    @Override
    public void delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Candidate guide = em.find(Candidate.class, id);
            if(guide == null){
                System.out.println("Guide can not be found so nothing more needs to be done it is already gone");
                em.getTransaction().rollback();
                return;
            }

            em.remove(guide);
            em.getTransaction().commit();
        }
    }

    public List<CandidateDTO> getAll(){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            List<Candidate> trips = em.createQuery("select t from Candidate t", Candidate.class).getResultList();
            return trips.stream().map(CandidateDTO::new).toList();

        }
    }

    public List<CandidateDTO> getAllWithCategory(Category category){
        try (EntityManager em = emf.createEntityManager()){
           return em.createQuery("SELECT c FROM Candidate c " +
                            "JOIN c.skills s " +
                            "WHERE s.category = :category", Candidate.class)
                    .setParameter("category", category)
                    .getResultList()
                    .stream()
                    .map(CandidateDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public HashMap<String, Object> getPopularityAndSalary(CandidateDTO dto) throws IOException {
        SkillStatsAPI skillStatsAPI = new SkillStatsAPI();
        HashMap<String, Object> response = new HashMap<>();

        if (dto.getSkills().isEmpty()){
            response.put("Candidate", dto);
            response.put("popularityScore", 0);
            response.put("averageSalary", 0);
            return response;
        }

        Data stats = skillStatsAPI.skillStats();

        int popularityScore = 0;
        int averageSalary = 0;

        List<EnrichedSkillDTO> enrichedSkills = new ArrayList<>();

        //For every skill the candidates have
        for (Skill skill : dto.getSkills()) {
            //We make an object that can hold a skills individual popularity and average salary
            EnrichedSkillDTO enrichedSkill = new EnrichedSkillDTO(skill);
            boolean found = false;

            for (Stats stat : stats.getData()) {
                //if a skills category and the foreign api category match
                if (skill.getCategory().toString().replace("_", "-").equals(stat.getCategoryKey().toUpperCase())) {
                    //we can get the info on a category such as
                    //popularity
                    enrichedSkill.setPopularityScore(stat.getPopularityScore());
                    //average salary
                    enrichedSkill.setAverageSalary(stat.getAverageSalary());
                    //we also get the total popularity number and total average pay for all the skills a candidate has
                    popularityScore += stat.getPopularityScore();
                    averageSalary += stat.getAverageSalary();
                    //and if we find a matching category we can set it to true to account for the criteria: If a skill is unknown in the external API, it is returned without enrichment data.
                    found = true;
                    break;
                }
            }
            //If there is not a matching category from the api we set its value as zero
            if (!found) {
                enrichedSkill.setPopularityScore(0);
                enrichedSkill.setAverageSalary(0);
            }

            enrichedSkills.add(enrichedSkill);
        }
        response.put("Candidate", dto);
        response.put("popularityScore", popularityScore);
        response.put("averageSalary", averageSalary);
        return response;
    }

    public void linkCandidatesToSkill(CandidateDTO cDTO, SkillDTO sDTO){
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Skill skill = em.find(Skill.class, sDTO.getId());
            Candidate candidate = em.find(Candidate.class, cDTO.getId());

            if(skill == null||candidate == null){
                System.out.println("Either skill or candidate could not be found");
                em.getTransaction().rollback();
                return;
            }

            if (!candidate.getSkills().contains(skill)) {
                candidate.getSkills().add(skill);
                skill.getCandidates().add(candidate);
            }
            em.getTransaction().commit();
        }
    }
}
