package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity(name = "ticket")
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
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
    @ManyToOne
    private Target target;
    private Integer customerPriority;
    private Integer teamPriority;
 /*   @OneToMany(mappedBy = "ticket")
    private Set<TicketMessage> ticketMessages;*/
    @ManyToOne
    @JoinTable(name = "Person_Tickets")
    private User customer;
    @OneToOne
    private Team team;
    @OneToOne@JoinColumn(name = "sameTicket")
    private Ticket sameTicket;
   /* @OneToMany(mappedBy = "ID")
    private Set<Ticket> dependentTickets;// che vuol dire dipendenti?  <-----*/
     /*   @Transient ALLEGATI
    private List<String> attachedFiles; */
    private Byte attached;

    public Ticket(String status, String dateStart, String category, String title, String description, Target target, Integer customerPriority, User customer, Byte attached) {
        this.status= status;
        this.dateStart = dateStart;
        this.category = category;
        this.title = title;
        this.description = description;
        this.target = target;
        this.customerPriority = customerPriority;
        this.customer = customer;
        this.attached = attached;


    }



    public void update(@NotNull Ticket ticketUpdated)
    {
        if(ticketUpdated.status !=null)
            this.status= ticketUpdated.status;
        if(dateEnd != null)
            this.dateEnd= ticketUpdated.dateEnd;
        if(ticketUpdated.category != null)
            this.category= ticketUpdated.category;
        if(ticketUpdated.customerPriority!=null)
            this.customerPriority= ticketUpdated.customerPriority;
        if(ticketUpdated.description != null)
            this.description=ticketUpdated.description;
        if(ticketUpdated.team != null)
            this.team = ticketUpdated.team;
        if(ticketUpdated.teamPriority!=null)
            this.teamPriority= ticketUpdated.teamPriority;
        if(ticketUpdated.title != null)
            this.title= ticketUpdated.title;
        if(ticketUpdated.sameTicket != null)
            this.sameTicket = ticketUpdated.sameTicket;
    }

    public String toString(){
        return this.ID.toString();
    }
}
