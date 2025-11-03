package app.controller;

import app.config.HibernateConfig;
import app.dao.EntityDAO;
import app.dto.EntityTemplateDTO;
import app.entity.EntityTemplate;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import jakarta.persistence.EntityManagerFactory;

public class EntityTemplateController {
    private final EntityDAO dao;

    public EntityTemplateController(){
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.dao = new EntityDAO(emf);

    }

    public void create(Context ctx){
        EntityTemplateDTO dto = ctx.bodyAsClass(EntityTemplateDTO.class);
        EntityTemplate name = dao.create(dto);

        ctx.status(HttpStatus.CREATED).json(name);
    }

    public void findByID(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        EntityTemplate name = dao.findById(id);
        EntityTemplateDTO dto = new EntityTemplateDTO(name);

        if(dto == null){
            ctx.status(HttpStatus.NOT_FOUND).result("The name could not be found");
        }else {
            ctx.status(HttpStatus.OK).json(dto);
        }
    }

    public void update(Context ctx){
        int id = Integer.parseInt(ctx.pathParam("id"));
        EntityTemplateDTO dto = ctx.bodyAsClass(EntityTemplateDTO.class);
        dto.setId(id);
        EntityTemplate name = dao.update(dto);
        EntityTemplateDTO dtoToBeSerialized = new EntityTemplateDTO(name);

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
