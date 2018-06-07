package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "relation")
@NoArgsConstructor
@Getter
@Setter
/*@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)*/
public class Relation {

    @Id
    @Column(name = "name")
    private String name;
    private Boolean cyclic;

    public Relation(String name, Boolean cyclic){
        this.name = name;
        this.cyclic = cyclic;
    }

}
