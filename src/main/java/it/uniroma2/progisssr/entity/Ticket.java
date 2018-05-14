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
    private Date dateStart;
    private Date dateEnd;
    private String category;
    private String title;
    private String description;
    @OneToOne //mappedby
    private Product product;
    private Integer custumerPriority;
    private Integer DispatcherPriority;
    @OneToMany(mappedBy = "ticket")
    private Set<TicketMessage> ticketMessages;
    @OneToOne
    private Utente customer;
    @OneToOne
    private Utente assistant;  //responsabile
    @OneToMany(mappedBy = "mainTicket")
    private Set<SubTicket> subTickets;
     /*   @Transient ALLEGATI
    private List<String> attachedFiles; */

    public Ticket(Date dateStart, String category, String title, String description, Product product, Integer custumerPriority, Utente customer) {
        this.status= State.NEW.toString();
        this.dateStart = dateStart;
        this.category = category;
        this.title = title;
        this.description = description;
        this.product = product;
        this.custumerPriority = custumerPriority;
        this.customer = customer;


    }



    public void aggiorna(@NotNull Ticket datiAggiornati) {
        this.status= datiAggiornati.status;
    }


}
