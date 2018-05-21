package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private String name;
    private String version;
    private String description;
    private String type;


    public Target(String name, String version, String description,String type) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.type = type;
    }


    public void update(@NotNull Target targetUpdated) {
        if (targetUpdated.name != null)
            this.name = targetUpdated.name;
        if(targetUpdated.version != null)
            this.version = targetUpdated.version;
        if(targetUpdated.description != null)
            this.description = targetUpdated.description;
        if(targetUpdated.type != null)
            this.type  = targetUpdated.type;

    }
}
