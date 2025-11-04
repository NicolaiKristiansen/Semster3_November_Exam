package app.dto;

import app.Category;
import app.entity.Candidate;
import app.entity.Skill;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties
@NoArgsConstructor
public class SkillDTO {
    private Integer id;
    private String name;
    private Category category;
    private String description;

    private List<Candidate> candidates = new ArrayList<>();

    public SkillDTO(int id, String name, Category category, String description, List<Candidate> candidates) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.candidates = candidates;
    }

    public SkillDTO(String name, Category category, String description, List<Candidate> candidates) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.candidates = candidates;
    }

    public SkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.category = skill.getCategory();
        this.description = skill.getDescription();
        if(!skill.getCandidates().isEmpty()){
            this.candidates = skill.getCandidates();
        }

    }

    public SkillDTO(String name, Category category, String description) {
        this.name = name;
        this.category = category;
        this.description = description;
    }
}
