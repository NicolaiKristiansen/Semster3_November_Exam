package app.dto;

import app.entity.EntityTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties
public class EntityTemplateDTO {

    private int id;
    private String variable;

    public EntityTemplateDTO(int id, String variable) {
        this.id = id;
        this.variable = variable;
    }

    public EntityTemplateDTO(String variable) {
        this.variable = variable;
    }

    public EntityTemplateDTO(EntityTemplate entityTemplate) {
        this.id = entityTemplate.getId();
        this.variable = entityTemplate.getVariable();
    }
}
