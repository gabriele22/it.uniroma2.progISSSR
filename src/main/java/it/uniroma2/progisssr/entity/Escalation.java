package it.uniroma2.progisssr.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "escalation")
@NoArgsConstructor
@Getter
@Setter
/*
entita utilizzata per mantenere i pesi inseriti dall'admin per calcolare il rank

 */
public class Escalation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "escalation_ID")
    private Long ID;
    private Double customerPriority;
    private Double teamPriority;
    private Double time;

    public Escalation(Double customerPriority, Double teamPriority, Double time) {
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
