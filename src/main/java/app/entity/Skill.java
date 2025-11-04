package app.entity;

import app.Category;
import app.dto.SkillDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Enumerated (value = EnumType.STRING)
    private Category category;
    private String description;

    @ManyToMany (mappedBy = "skills", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Candidate> candidates = new ArrayList<>();

    public Skill(int id, String name, Category category, String description, List<Candidate> candidates) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.candidates = candidates;
    }

    public Skill(String name, Category category, String description, List<Candidate> candidates) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.candidates = candidates;
    }

    public Skill(SkillDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.category = dto.getCategory();
        this.description = dto.getDescription();
        this.candidates = dto.getCandidates();
    }
}
