package app.dto;

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
public class CandidateDTO {
    private Integer id;
    private String name;
    private Integer phone;
    private String educationBackground;

    private List<Skill> skills = new ArrayList<>();

    public CandidateDTO(String name, int phone, String educationBackground, List<Skill> skills) {
        this.name = name;
        this.phone = phone;
        this.educationBackground = educationBackground;
        this.skills = skills;
    }

    public CandidateDTO(String name, int phone, String educationBackground) {
        this.name = name;
        this.phone = phone;
        this.educationBackground = educationBackground;
    }

    public CandidateDTO(Candidate candidate) {
        this.id = candidate.getId();
        this.name = candidate.getName();
        this.phone = candidate.getPhone();
        this.educationBackground = candidate.getEducationBackground();
        if (!candidate.getSkills().isEmpty()) {
            this.skills = candidate.getSkills();
        }
    }
}
