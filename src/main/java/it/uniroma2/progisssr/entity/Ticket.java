package it.uniroma2.progisssr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    @OneToOne@Transient
    private Team team;
    //si legano i ticket uguali sempre al main ticket già presente nel sistema
    @OneToOne@JoinColumn(name = "sameTicket")@JsonIgnoreProperties
    private Ticket sameTicket;
    @ManyToMany@JoinTable(name = "dependentTickets")@JsonIgnoreProperties
    private Set<Ticket> dependentTickets;
    private Integer countDependencies;
    @ManyToMany
    @JoinTable(name = "regressionTicketsGenerator")
    @JsonIgnoreProperties
    private Set<Ticket> regressionTicketsGenerator;
    /*   @Transient ALLEGATI
    private List<String> attachedFiles; */
    private Byte attached;

    private String teamComment;

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
        if(ticketUpdated.teamComment != null)
            this.teamComment = ticketUpdated.teamComment;
    }

    //return true if there isn't cycle
    public boolean isAcycle(@NotNull Ticket dependentTicket){

        if(this.dependentTickets.isEmpty())
            return true;
        if(this.dependentTickets.contains(dependentTicket))
            return false;
        else{
            for(Ticket t: this.dependentTickets) {
                if (!t.isAcycle(dependentTicket))
                    return false;
            }
        }

        return true;

    }

    public boolean isAlreadyDependent(@NotNull Ticket depedentTicket){
        return this.dependentTickets.contains(depedentTicket);
    }

    public void addDependentTickets(@NotNull Ticket dependentTicket) {

        this.dependentTickets.add(dependentTicket);

    }

    public Integer addCount(){
        if(this.countDependencies==null)
            this.countDependencies=0;
        this.countDependencies++;
        return this.countDependencies;
    }

    public Integer decreaseCount(){
        if(this.countDependencies==null)
            this.countDependencies=0;
        this.countDependencies--;
        return this.countDependencies;
    }

    public Set<Ticket> decreaseDependents(){
        for(Ticket t: this.dependentTickets){
            t.decreaseCount();
        }
        return this.dependentTickets;

    }


    public String toString(){
        return this.ID.toString();
    }

    public void addRegression(Ticket ticketGenerator) {
        this.regressionTicketsGenerator.add(ticketGenerator);
    }


}
