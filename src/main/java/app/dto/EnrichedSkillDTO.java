package app.dto;

import app.entity.Skill;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrichedSkillDTO {
    private Integer id;
    private String name;
    private int popularityScore;
    private int averageSalary;
    private String category;

    public EnrichedSkillDTO(Skill skill) {
        this.id = skill.getId();
        this.name = skill.getName();
        this.category = skill.getCategory().toString();
        this.popularityScore = 0;  // Default value
        this.averageSalary = 0;    // Default value
    }
}
