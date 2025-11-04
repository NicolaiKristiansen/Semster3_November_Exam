package app.poulator;

import app.Category;
import app.config.HibernateConfig;
import app.dao.CandidateDAO;
import app.dao.SkillDAO;
import app.dto.CandidateDTO;
import app.dto.SkillDTO;
import app.exceptions.EntityNotFoundException;
import app.security.daos.SecurityDAO;
import app.security.entities.Role;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class Populator {
    public static void main(String[] args) throws EntityNotFoundException {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        SkillDAO skillDAO = new SkillDAO(emf);
        CandidateDAO candidateDAO = new CandidateDAO(emf);
        SecurityDAO securityDAO = new SecurityDAO(emf);

        List<SkillDTO> skillDTOS = new ArrayList<>();
        List<CandidateDTO> candidateDTOS = new ArrayList<>();
        List<Role> roles = new ArrayList<>();

        Role role1 = new Role("User"); roles.add(role1);
        Role role2 = new Role("Admin"); roles.add(role2);
        Role role3 = new Role("Anyone"); roles.add(role3);
        //Fill in with Entity objects and use loops to add them to database by using create function
        SkillDTO skill1 = new SkillDTO("Java, Python, C#, JavaScript", Category.PROG_LANG, "General-purpose programming languages"); skillDTOS.add(skill1);
        SkillDTO skill2 = new SkillDTO("PostgreSQL, MySQL, MongoDB", Category.DB,"Databases and data storage technologies"); skillDTOS.add(skill2);
        SkillDTO skill3 = new SkillDTO("Docker, Kubernetes, GitHub Actions", Category.DEVOPS, "Tools and practices for deployment, CI/CD, and infrastructure"); skillDTOS.add(skill3);
        SkillDTO skill4 = new SkillDTO("HTML, CSS, TypeScript, Vue.js", Category.FRONTEND, "Front-end and UI-related technologies"); skillDTOS.add(skill4);
        SkillDTO skill5 = new SkillDTO("JUnit, Cypress, Jest", Category.TESTING, "Tools and frameworks for testing and QA"); skillDTOS.add(skill5);
        SkillDTO skill6 = new SkillDTO("Pandas, TensorFlow, Power BI", Category.DATA, "Data science, analytics, and machine learning tools"); skillDTOS.add(skill6);
        SkillDTO skill7 = new SkillDTO("Spring Boot, React, Angular", Category.FRAMEWORK, "Application frameworks and libraries"); skillDTOS.add(skill7);

        CandidateDTO candidate1 = new CandidateDTO("Nicolai", 4643673, "Datamatiker"); candidateDTOS.add(candidate1);
        CandidateDTO candidate2 = new CandidateDTO("Kevin", 784353, "Computer Science"); candidateDTOS.add(candidate2);
        CandidateDTO candidate3 = new CandidateDTO("Anders", 78464453, "Programmering"); candidateDTOS.add(candidate3);
        CandidateDTO candidate4 = new CandidateDTO("Kelly", 839895, "Software Udvikling"); candidateDTOS.add(candidate4);

        for (SkillDTO dto: skillDTOS){
            skillDAO.create(dto);
        }

        for (CandidateDTO dto: candidateDTOS){
            candidateDAO.create(dto);
        }

        //For security make sure to make an admin User object and save it
        for (Role role: roles){
            securityDAO.createRole(role.getRoleName());
        }

        securityDAO.createUser("admin", "adminpassword");
        securityDAO.addUserRole("admin", role2.getRoleName());

    }
}
