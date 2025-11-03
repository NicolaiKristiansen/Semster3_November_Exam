package app.dao;

import app.dto.EntityTemplateDTO;
import app.entity.EntityTemplate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityDAO implements IDAO<EntityTemplate, EntityTemplateDTO> {
    private final EntityManagerFactory emf;

    public EntityDAO(EntityManagerFactory emf){

        this.emf = emf;
    }

    @Override
    public EntityTemplate create(EntityTemplateDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            EntityTemplate name = new EntityTemplate(dto);
            em.persist(name);
            em.getTransaction().commit();
            return name;
        }
    }

    @Override
    public EntityTemplate findById(int id) {
        try(EntityManager em = emf.createEntityManager()){
            EntityTemplate name = em.find(EntityTemplate.class, id);
            if(name == null){
                System.out.println("Couldn't find the name with id:" + id);
                return null;
            }
            return name;
        }
    }

    @Override
    public EntityTemplate update(EntityTemplateDTO dto) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            EntityTemplate name = em.find(EntityTemplate.class, dto.getId());

            if(name == null){
                System.out.println("Could not find the guide to be updated");
                em.getTransaction().rollback();
                return null;
            }

            if(dto.getVariable() !=null){
                name.setVariable(dto.getVariable());
            }

            em.getTransaction().commit();
            return name;
        }
    }

    @Override
    public void delete(int id) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            EntityTemplate guide = em.find(EntityTemplate.class, id);
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
