package it.uniroma2.progisssr.entity;

import it.uniroma2.progisssr.utils.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_ID")
    private Long ID;
    private String status;
    private String dateStart;
    private String dateEnd;
    private String category;
    private String title;
    private String description;
    @OneToOne
    private Product product;
    private Integer customerPriority;
    private Integer teamPriority;
 /*   @OneToMany(mappedBy = "ticket")
    private Set<TicketMessage> ticketMessages;*/
    @OneToOne
    private User customer;
    @OneToOne
    private Team team;
    @OneToMany(mappedBy = "mainTicket")
    private Set<SubTicket> subTickets;
    @OneToMany(mappedBy = "ID")
    private Set<Ticket> sameTopicTickets;
    @OneToMany(mappedBy = "ID")
    private Set<Ticket> dependentTickets;// che vuol dire dipendenti?  <-----
     /*   @Transient ALLEGATI
    private List<String> attachedFiles; */

    public Ticket(String dateStart, String category, String title, String description, Product product, Integer customerPriority, User customer) {
        this.status= State.NEW.toString();
        this.dateStart = dateStart;
        this.category = category;
        this.title = title;
        this.description = description;
        this.product = product;
        this.customerPriority = customerPriority;
        this.customer = customer;


    }



    public void aggiorna(@NotNull Ticket datiAggiornati) {
        this.status= datiAggiornati.status;
    }


}
