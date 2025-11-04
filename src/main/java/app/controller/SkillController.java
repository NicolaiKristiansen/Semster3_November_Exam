package app.controller;

import app.config.HibernateConfig;
import app.dao.SkillDAO;

import app.dto.SkillDTO;
import app.entity.Skill;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

public class SkillController {
    private final SkillDAO dao;

    public SkillController(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = new SkillDAO(emf);

    }

    public void create(Context ctx){
        SkillDTO dto = ctx.bodyAsClass(SkillDTO.class);
        Skill name = dao.create(dto);

        ctx.status(HttpStatus.CREATED).json(name);
    }

    public void findByID(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        Skill name = dao.findById(id);
        SkillDTO dto = new SkillDTO(name);

        if(dto == null){
            ctx.status(HttpStatus.NOT_FOUND).result("The name could not be found");
        }else {
            ctx.status(HttpStatus.OK).json(dto);
        }
    }

    public void update(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        SkillDTO dto = ctx.bodyAsClass(SkillDTO.class);
        dto.setId(id);
        Skill name = dao.update(dto);
        SkillDTO dtoToBeSerialized = new SkillDTO(name);

        if(dtoToBeSerialized == null){
            ctx.status(404).json("name with id " + id + " not found.");

        }else {
            ctx.status(200).json(dtoToBeSerialized);
        }
    }

    public void delete(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        dao.delete(id);

        if(dao.findById(id) == null) {
            ctx.result("Guide has been deleted");
        }
    }
}
