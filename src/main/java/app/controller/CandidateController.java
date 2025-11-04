package app.controller;

import app.Category;
import app.config.HibernateConfig;
import app.dao.CandidateDAO;
import app.dao.SkillDAO;
import app.dto.CandidateDTO;
import app.dto.EnrichedSkillDTO;
import app.dto.SkillDTO;
import app.entity.Candidate;
import app.entity.Skill;
import app.utils.externalAPI.Data;
import app.utils.externalAPI.SkillStatsAPI;
import app.utils.externalAPI.Stats;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CandidateController {
    private final CandidateDAO cDAO;
    private final SkillDAO sDAO;


    public CandidateController(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.cDAO = new CandidateDAO(emf);
        this.sDAO = new SkillDAO(emf);

    }

    public void create(Context ctx){
        CandidateDTO dto = ctx.bodyAsClass(CandidateDTO.class);
        Candidate candidate = cDAO.create(dto);

        ctx.status(HttpStatus.CREATED).json(candidate);
    }

    public void findByID(Context ctx) throws IOException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        Candidate candidate = cDAO.findById(id);


        if(candidate == null){
            ctx.status(HttpStatus.NOT_FOUND).result("The candidate could not be found");
            return;
        }

        CandidateDTO dto = new CandidateDTO(candidate);
        //If a candidates doesn't have any skills we return an empty list
        HashMap<String, Object> response = cDAO.getPopularityAndSalary(dto);
        ctx.status(HttpStatus.OK).json(response);

    }

    public void update(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        CandidateDTO dto = ctx.bodyAsClass(CandidateDTO.class);
        dto.setId(id);
        Candidate candidate = cDAO.update(dto);
        if(candidate == null){
            ctx.status(HttpStatus.NOT_FOUND).json("candidate with id " + id + " not found.");
            return;
        }

        CandidateDTO dtoToBeSerialized = new CandidateDTO(candidate);

        ctx.status(HttpStatus.OK).json(dtoToBeSerialized);

    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        cDAO.delete(id);

        if(cDAO.findById(id) == null) {
            ctx.result("Guide has been deleted");
        }
    }

    public void getAll(Context ctx){
        String stringCategory = ctx.queryParam("category");

        if(stringCategory != null){
            Category category = Category.valueOf(stringCategory.toUpperCase());
            List<CandidateDTO> candidatesWithCategory = cDAO.getAllWithCategory(category);
            if(!candidatesWithCategory.isEmpty()) {
                ctx.status(HttpStatus.OK).json(candidatesWithCategory);
                return;
            }
        }

        List<CandidateDTO> trips = cDAO.getAll();

        if(trips.isEmpty()){
            ctx.status(HttpStatus.NOT_FOUND).result("The list is empty");
        }
        ctx.status(HttpStatus.OK).json(trips);
    }

    public void linkCandidateToSkill(Context ctx){
        int candidateId = Integer.parseInt(ctx.pathParam("id"));
        int skillId = Integer.parseInt(ctx.pathParam("skillId"));

        Candidate candidate = cDAO.findById(candidateId);
        Skill skill = sDAO.findById(skillId);

        CandidateDTO cDTO = new CandidateDTO(candidate);
        SkillDTO sDTO = new SkillDTO(skill);

        cDAO.linkCandidatesToSkill(cDTO, sDTO);

        if (cDTO.getSkills().contains(sDTO) && sDTO.getCandidates().contains(cDTO)){
            ctx.status(HttpStatus.OK).result("Candidate: " + cDTO.getName() + " has added the skill: " + sDTO.getName());
        }
    }

    public void getTopByPopularity(Context ctx) throws IOException {
        List<CandidateDTO> allCandidates = cDAO.getAll();

        int highestPopularityScore  = 0;
        int idOfTopCandidate  = 0;

        for (CandidateDTO candidateDTO: allCandidates){
            HashMap<String, Object> candidateInfo = cDAO.getPopularityAndSalary(candidateDTO);
            candidateInfo.get("popularityScore");

            int currentPopularityScore  = (int)candidateInfo.get("popularityScore");
            if (highestPopularityScore  < currentPopularityScore ){
                highestPopularityScore  = currentPopularityScore ;
                idOfTopCandidate = candidateDTO.getId();
            }

            Candidate mostPopularCandidate = cDAO.findById(idOfTopCandidate);
            if (mostPopularCandidate == null) {
                ctx.status(HttpStatus.NOT_FOUND).result("No candidates found.");
            } else {
                HashMap<String, Object> response = new HashMap<>();
                response.put("id", mostPopularCandidate.getId());
                response.put("candidate", mostPopularCandidate.getName());
                response.put("popularityScore", highestPopularityScore);
                ctx.status(HttpStatus.OK).json(response);
            }
        }
    }
}
