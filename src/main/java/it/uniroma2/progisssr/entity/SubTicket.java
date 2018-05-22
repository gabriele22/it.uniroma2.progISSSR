package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SubTicket extends Ticket {

    @ManyToOne
    @JoinTable(name = "Ticket_SubTicket")
    private Ticket mainTicket;

    public SubTicket(String state, String dateStart, String category, String title, String description, Target target, Integer custumerPriority, User customer) {
        super(state, dateStart, category, title, description, target, custumerPriority, customer);
    }


}
