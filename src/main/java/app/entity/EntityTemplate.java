package app.entity;

import app.dto.EntityTemplateDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties
public class EntityTemplate {

    @Id
    //Used to give id an auto generated number using the postgresql's auto increment feature
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public int id;
    private String variable;

    /*@OneToMany
    You can add a relatioship between entitys with @OneToMany, @ManyToMany*/


    public EntityTemplate (String variable){
        this.variable = variable;
    }

    public EntityTemplate (EntityTemplateDTO entityTemplateDTO){}
}
