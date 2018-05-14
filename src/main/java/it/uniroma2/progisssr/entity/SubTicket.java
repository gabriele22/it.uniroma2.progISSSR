package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SubTicket extends Ticket {

    @ManyToOne
    private Ticket mainTicket;

    public SubTicket(Date dateStart, String category, String title, String description, Product product, Integer custumerPriority, Utente customer) {
        super(dateStart, category, title, description, product, custumerPriority, customer);
    }


}
