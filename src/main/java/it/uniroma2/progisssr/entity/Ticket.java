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
//NB: annotation to specify to Spring that this class is persistent
@Table(name = "ticket")
//NB: specify the name of the table in the DB associated to this class
@NoArgsConstructor
//NB: Lombok, generate in auto a constructor with no argoments at run time
@Getter
@Setter
//NB: Lombok, generate in auto getter and setter at run time
@Inheritance(strategy = InheritanceType.JOINED)
//NB: specify the strategy use to map a generalization. In particular
//      SINGLE_TABLE: tutte le generalizzazioni accorpate nell'entità padre, con l'aggiunta di un attributo disciminante
//      JOINED: le classi figlie mantengono solo i loro attributi caratteristici e un fk alla classe padre
//      TABLE_PER_CLASS: le classi figlie mantengono tutti i loro attributi, compresi quelli del padre (senza fk)
public class Ticket {

    @Id
    //NB: specify the attibute id of this class
    @GeneratedValue(strategy = GenerationType.AUTO)
    //NB: auto generate the id of this class
    @Column(name = "ticket_ID")
    //NB: specify the name of the column associated to this attribute
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
    @ManyToOne
    @JoinTable(name = "Person_Tickets")
    private User customer;
    @OneToOne
    @JoinColumn(name = "teamName")
    @JsonIgnoreProperties
    //NB: evita che la libreria jackson generi una ricorsione infinita nella conversione di questo attributo in formato json
    private Team team;
    @OneToOne
    @JoinColumn(name = "sameTicket")
    @JsonIgnoreProperties
    //NB: tutti i ticket appertenenti ad una stessa classe della relazione "uguaglianza" fanno riferimento ad un unico
    //  ticket principale
    private Ticket sameTicket;
    @ManyToMany
    @JoinTable(name = "dependent_tickets")
    @JsonIgnoreProperties
    //NB: lista dei ticket che dipendono da questa istanza di ticket
    private Set<Ticket> dependentTickets;
    //NB: indica il numero di ticket non risolti da cui dipende questa istanza di ticket
    private Integer countDependencies;
    @ManyToMany
    @JoinTable(name = "regressionTicketsGenerator")
    @JsonIgnoreProperties
    //NB: lista di ticket in relazione di "regressione" con questa istanza di ticket
    private Set<Ticket> regressionTicketsGenerator;
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
    }

    public void update(@NotNull Ticket ticketUpdated)
    {
        if(ticketUpdated.status !=null)
            this.status= ticketUpdated.status;
        if(ticketUpdated.dateEnd != null)
            this.dateEnd= ticketUpdated.dateEnd;
        if (ticketUpdated.dateExecutionStart != null)
            this.dateExecutionStart = ticketUpdated.dateExecutionStart.split(" ")[0];
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

    //NB: metodo controlla se l'aggiunta di una relazione tra questa istanza di ticket e dependentTicket crea un ciclo
    //  tra le relazione. Ritorna la lista dei ticket coinvoilti nel ciclo in caso si crei quest'ultimo, altrimenti
    //  torna una lista vuota se non si formano cicli
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

    //NB: ritorna vero se dependentTicket dipennde già da questo ticket, falso altrimenti
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

    //NB: calcolo il rank del ticket
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
}
