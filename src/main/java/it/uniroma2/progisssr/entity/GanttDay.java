package it.uniroma2.progisssr.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.uniroma2.progisssr.embeddable.KeyGanttDay;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "ganttDay")
@NoArgsConstructor
@Getter
@Setter
public class GanttDay {

/*    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "day_ID")
    private Long ID;
    private String day;*/

    @EmbeddedId
    private KeyGanttDay keyGanttDay;
    private Double availability;
    /*@OneToOne@JoinColumn(name = "teamName")@JsonIgnoreProperties
    private Team team;*/
    @ManyToMany@JsonIgnoreProperties @JoinTable(name = "ticketsPerDay")
    private Set<Ticket> tickets;
/*
    public GanttDay(String day, Double availability, Team team, Set<Ticket> tickets) {
*//*        this.day = day;
        this.availability = availability;
        this.team = team;*//*
        this.keyGanttDay = new KeyGanttDay(day,team);
        this.tickets = tickets;
        this.availability = availability;
    }*/

    public GanttDay(KeyGanttDay keyGanttDay, Double availability, Set<Ticket> tickets) {
        this.keyGanttDay = keyGanttDay;
        this.availability = availability;
        this.tickets = tickets;
    }

    public void update(@NotNull GanttDay ganttDayUpdated) {
        if(ganttDayUpdated.keyGanttDay.getDay()!= null)
            this.keyGanttDay.setDay(ganttDayUpdated.keyGanttDay.getDay());
        if(ganttDayUpdated.availability != null)
            this.availability = ganttDayUpdated.availability;
        if(ganttDayUpdated.keyGanttDay.getTeam() != null)
            this.keyGanttDay.setTeam(ganttDayUpdated.keyGanttDay.getTeam());
        if(ganttDayUpdated.tickets != null)
            this.tickets = ganttDayUpdated.tickets;

    }
}
