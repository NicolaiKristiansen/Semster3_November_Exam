package app.poulator;

import app.config.HibernateConfig;
import app.dao.EntityDAO;
import app.dto.EntityTemplateDTO;
import app.exceptions.EntityNotFoundException;
import app.security.daos.SecurityDAO;
import app.security.entities.Role;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {
    public static void main(String[] args) throws EntityNotFoundException {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        EntityDAO entityDAO = new EntityDAO(emf);
        SecurityDAO securityDAO = new SecurityDAO(emf);

        List<EntityTemplateDTO> entityTemplateDTOS = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        Role role1 = new Role("User"); roles.add(role1);
        Role role2 = new Role("Admin"); roles.add(role2);
        Role role3 = new Role("Anyone"); roles.add(role3);
        //Fill in with Entity objects and use loops to add them to database by using create function

        //For security make sure to make an admin User object and save it
        for (Role role: roles){
            securityDAO.createRole(role.getRoleName());
        }

        securityDAO.createUser("admin", "adminpassword");
        securityDAO.addUserRole("admin", role2.getRoleName());

    }
}
