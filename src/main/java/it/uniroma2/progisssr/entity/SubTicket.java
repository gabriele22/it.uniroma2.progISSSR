package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SubTicket {

    @Id
    private String id;
    public SubTicket(String id){
        this.id=id;
    }
/*        extends Ticket {

    @ManyToOne
    @JoinTable(name = "Ticket_SubTicket")
    private Ticket mainTicket;

    public SubTicket(String state, String dateStart, String category, String title, String description, Target target, Integer custumerPriority, User customer,Byte attached) {
        super(state, dateStart, category, title, description, target, custumerPriority, customer, attached );
    }*/
}
