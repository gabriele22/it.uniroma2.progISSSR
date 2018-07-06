package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.embeddable.KeyGanttDay;
import it.uniroma2.progisssr.entity.GanttDay;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GanttDayDao extends JpaRepository<GanttDay,KeyGanttDay> {

    @Query("SELECT g.availability FROM GanttDay g where g.keyGanttDay = :keyGanttDay")
    Double getAvailabilityByDayAndTeam(@Param("keyGanttDay") KeyGanttDay keyGanttDay);


    GanttDay findByKeyGanttDay(KeyGanttDay keyGanttDay);

    @Query("SELECT g.tickets FROM GanttDay g where g.keyGanttDay = :keyGanttDay")
    List<Ticket> getTicketsByKey(@Param("keyGanttDay") KeyGanttDay keyGanttDay);

    @Query("SELECT g.tickets FROM GanttDay g where g.keyGanttDay = :keyGanttDay")
    Set<Ticket> getTicketsSetByKey(@Param("keyGanttDay") KeyGanttDay keyGanttDay);
}
