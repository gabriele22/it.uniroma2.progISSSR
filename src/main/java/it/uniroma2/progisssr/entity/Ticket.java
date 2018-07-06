package it.uniroma2.progisssr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.uniroma2.progisssr.utils.ParseDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "ticket")
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
    private String datePendingStart;
    private String dateExecutionStart;
    private Integer durationEstimation;
    private String dateEnd;
    private String category;
    private String title;
    private String description;
    @ManyToOne
    private Target target;
    private Integer customerPriority;
    private Integer teamPriority;
    private Double rank;
    private Double difficulty;
 /*   @OneToMany(mappedBy = "ticket")
    private Set<TicketMessage> ticketMessages;*/
    @ManyToOne
    @JoinTable(name = "Person_Tickets")
    private User customer;
    @OneToOne@JoinColumn(name = "teamName")@JsonIgnoreProperties
    private Team team;
    //si legano i ticket uguali sempre al main ticket già presente nel sistema
    @OneToOne@JoinColumn(name = "sameTicket")@JsonIgnoreProperties
    private Ticket sameTicket;
    @ManyToMany
    @JoinTable(name = "dependent_tickets")
    @JsonIgnoreProperties
    private Set<Ticket> dependentTickets;
    private Integer countDependencies;
    @ManyToMany
    @JoinTable(name = "regressionTicketsGenerator")
    @JsonIgnoreProperties
    private Set<Ticket> regressionTicketsGenerator;
    /*@OneToMany
    @JoinTable(name = "relationTicket")
    @JsonIgnoreProperties
    private Set<RelationInstance> relations;*/
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
        if(ticketUpdated.dateEnd != null)
            this.dateEnd= ticketUpdated.dateEnd;
        if (ticketUpdated.dateExecutionStart != null)
            this.dateExecutionStart = ticketUpdated.dateExecutionStart;
        if (ticketUpdated.durationEstimation != null)
            this.durationEstimation = ticketUpdated.durationEstimation;
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
        if(ticketUpdated.sameTicket != null && !ticketUpdated.sameTicket.equals(this))
            this.sameTicket = ticketUpdated.sameTicket;
        if(ticketUpdated.teamComment != null)
            this.teamComment = ticketUpdated.teamComment;
        if(ticketUpdated.difficulty != null)
            this.difficulty = ticketUpdated.difficulty;

    }

    //return true if there isn't cycle
    public List<Ticket> isAcycle(@NotNull Ticket dependentTicket, List<Ticket> cycle){

        List<Ticket> newCycle = new ArrayList<>();
        //newCycle.addAll(cycle);
        if(this.dependentTickets.isEmpty())
            return newCycle;
        if(this.dependentTickets.contains(dependentTicket)) {
            cycle.add(dependentTicket);
            cycle.add(this);
            newCycle.addAll(cycle);
            return newCycle;
        }
        else{
            for(Ticket t: this.dependentTickets) {
                if (!t.isAcycle(dependentTicket, cycle).isEmpty()) {
                    cycle.add(this);
                    newCycle.addAll(cycle);
                    return newCycle;
                }
            }
        }

        return newCycle;

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

    public Double computeRank(Double a, Double b, Double c) {
        Calendar now = Calendar.getInstance();
        GregorianCalendar datePendingStart =  ParseDate.parseGregorianCalendar(this.datePendingStart);
        Long waitingTime = (now.getTimeInMillis() -datePendingStart.getTimeInMillis());

        Double waitingTimeInHour=  waitingTime.doubleValue()/(1000*3600);
        if(this.customerPriority==null)
            this.customerPriority=0;
        if(this.teamPriority == null)
            this.teamPriority =0;
        return  a * this.customerPriority + b * this.teamPriority + c * waitingTimeInHour;
    }

    public void updateRank( Double rankUpdated){
        this.rank= rankUpdated;
    }
    public String toString(){
        return this.ID.toString();
    }
    public void addRegression(Ticket ticketGenerator) {
        this.regressionTicketsGenerator.add(ticketGenerator);
    }
    public void updateDuration(Integer duration) {
        this.durationEstimation= duration;
    }
    public void updateDateExecutionStart(String date){
        this.dateExecutionStart = date;
    }



    /*public Boolean isCountDependenciesZero() {
        if (this.countDependencies == 0)
            return true;
        else return false;
    }*/
/*
    public int compareRank(Ticket ticket) {
        return Double.compare(this.rank, ticket.rank);
    }*/

}
