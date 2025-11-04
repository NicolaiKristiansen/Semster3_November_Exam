package app.entity;

import app.dto.CandidateDTO;
import app.dto.SkillDTO;
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
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int phone;
    private String educationBackground;

    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "candidate_skill",
            joinColumns = @JoinColumn(name = "candidate_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills = new ArrayList<>();

    public Candidate(int id, String name, int phone, String educationBackground, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.educationBackground = educationBackground;
        this.skills = skills;
    }

    public Candidate(String name, int phone, String educationBackground, List<Skill> skills) {
        this.name = name;
        this.phone = phone;
        this.educationBackground = educationBackground;
        this.skills = skills;
    }

    public Candidate(CandidateDTO dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.phone = dto.getPhone();
        this.educationBackground = dto.getEducationBackground();
        this.skills = dto.getSkills();
    }
}
