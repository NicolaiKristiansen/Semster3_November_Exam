package app.dao;

import app.dto.SkillDTO;
import app.entity.Skill;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class SkillDAO implements IDAO<Skill, SkillDTO> {
    private final EntityManagerFactory emf;

    public SkillDAO(EntityManagerFactory emf){

        this.emf = emf;
    }

    @Override
    public Skill create(SkillDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Skill skill = new Skill(dto);
            em.persist(skill);
            em.getTransaction().commit();
            return skill;
        }
    }

    @Override
    public Skill findById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            Skill skill = em.find(Skill.class, id);
            if(skill == null){
                System.out.println("Couldn't find the skill with id:" + id);
                return null;
            }
            return skill;
        }
    }

    @Override
    public Skill update(SkillDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Skill skill = em.find(Skill.class, dto.getId());

            if(skill == null){
                System.out.println("Could not find the skill to be updated");
                em.getTransaction().rollback();
                return null;
            }

            if(dto.getId() !=null){
                skill.setId(dto.getId());
            }
            if(dto.getName() !=null){
                skill.setName(dto.getName());
            }
            if(dto.getCategory() !=null){
                skill.setCategory(dto.getCategory());
            }
            if(dto.getDescription() !=null){
                skill.setDescription(dto.getDescription());
            }
            if(dto.getCandidates() !=null){
                skill.setCandidates(dto.getCandidates());
            }

            em.getTransaction().commit();
            return skill;
        }
    }

    @Override
    public void delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            Skill guide = em.find(Skill.class, id);
            if(guide == null){
                System.out.println("Guide can not be found so nothing more needs to be done it is already gone");
                em.getTransaction().rollback();
                return;
            }

            em.remove(guide);
            em.getTransaction().commit();
        }
    }
}
