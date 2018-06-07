package it.uniroma2.progisssr.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "escalation")
@NoArgsConstructor
@Getter
@Setter
public class Escalation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "escalation_ID")
    private Long ID;
    private Integer customerPriority;
    private Integer teamPriority;
    private Integer time;

    public Escalation(Integer customerPriority, Integer teamPriority, Integer time) {
        this.customerPriority = customerPriority;
        this.teamPriority = teamPriority;
        this.time = time;
    }


    public void update(@NotNull Escalation escalationUpdate){
        this.customerPriority = escalationUpdate.customerPriority;
        this.teamPriority = escalationUpdate.teamPriority;
        this.time = escalationUpdate.time;
    }
}
