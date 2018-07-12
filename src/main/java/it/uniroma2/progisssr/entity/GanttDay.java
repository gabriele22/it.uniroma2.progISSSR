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

    @EmbeddedId
    //annotation to specify that the Id is a class (composite primary key) that is embedded to this
    private KeyGanttDay keyGanttDay;
    private Double availability;
    @ManyToMany
    @JsonIgnoreProperties
    @JoinTable(name = "ticketsPerDay")
    private Set<Ticket> tickets;

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
