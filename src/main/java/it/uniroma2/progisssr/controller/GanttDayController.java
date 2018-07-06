package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.GanttDayDao;
import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.embeddable.KeyGanttDay;
import it.uniroma2.progisssr.entity.GanttDay;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.utils.ParseDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
public class GanttDayController {
    @Autowired
    GanttDayDao ganttDayDao;
    @Autowired
    TeamDao teamDao;
    @Autowired
    TicketDao ticketDao;


    @Transactional
    public GanttDay createGanttDay(GanttDay ganttDay){
        GanttDay ganttDayNew = ganttDayDao.save(ganttDay);
        return ganttDayNew;
    }


    @Transactional
    public @NotNull List<GanttDay> createGanttInstance(@NotNull Ticket ticket, @NotNull String teamName,
                                               @NotNull String firstDay, @NotNull Integer duration,
                                               @NotNull Long ticketId) {

        List<GanttDay> ganttDays = new ArrayList<>();

        Team team = teamDao.getOne(teamName);
        Ticket ticketToUpdate = ticketDao.getOne(ticketId);
        Ticket ticketAssigned = null;

        GregorianCalendar first = ParseDate.parseGregorianCalendar(firstDay);

        //check availability for all day
        for(int i = 0; i<duration; i++) {
            first.add(Calendar.DATE, i);
            String currentDay = ParseDate.gregorianCalendarToString(first);
            KeyGanttDay keyGanttDay = new KeyGanttDay(currentDay,team);
            if (!ganttDayDao.existsById(keyGanttDay)) {
                continue;
            }
            Double currentAvail = ganttDayDao.getAvailabilityByDayAndTeam(keyGanttDay);
            if (currentAvail >= 1) {
                return null;
            }
        }

        for(int i = 0; i<duration; i++) {
            first.add(Calendar.DATE, i);
            String currentDay = ParseDate.gregorianCalendarToString(first);
            ganttDays.add(updateGanttDay(currentDay, ticketToUpdate, team));
        }

        ticketToUpdate.update(ticket);
        ticketAssigned = ticketDao.save(ticketToUpdate);

        //return ticketAssigned;

        return ganttDays;
    }


    private Double computeAvailability(Team team, Integer ticketsSize) {
        List<User> teamMember = teamDao.findTeamMembersByTeam(team);
        return (double) ticketsSize /(double)teamMember.size();

    }


    private GanttDay updateGanttDay( String day, Ticket ticketToUpdate , Team team) {
        /*if(ganttDay == null) {
            Double availability = computeAvailability(day, team);
            Set<Ticket> ticketSet = new HashSet<>();
            ticketSet.add(ticket);
            GanttDay ganttDayNew = new GanttDay(day, availability, team,ticketSet);
            ganttDayDao.save(ganttDayNew);
            ticketToUpdate.update(ticket);
            Ticket ticketAssigned = ticketDao.save(ticketToUpdate);
            return ticketAssigned;

        }else{*/
        KeyGanttDay keyGanttDay = new KeyGanttDay(day, team);
            /*Double currentAvail = ganttDayDao.getAvailabilityByDayAndTeam(keyGanttDay);
            if(currentAvail<1) {*/
        Set<Ticket> ticketSet = new HashSet<>();
        GanttDay ganttDayToUpdate;
       /* if (!ganttDayDao.existsById(keyGanttDay)) {*/
            //GanttDay ganttDay = new GanttDay(currentDay, 0.0, team, null);
            Set<Ticket> tickets =  new HashSet<>();
            GanttDay ganttDayToSave = new GanttDay(keyGanttDay,0.0,ticketSet);
            ganttDayToUpdate = ganttDayDao.save(ganttDayToSave);
            /*ganttDayDao.saveAndFlush(ganttDay);*/
       /* } else{
            ganttDayToUpdate = ganttDayDao.findByKeyGanttDay(keyGanttDay);
            ticketSet = ganttDayDao.getTicketsSetByKey(keyGanttDay);
        }*/
        ticketSet.add(ticketToUpdate);
        Double newAvail = computeAvailability(team, ticketSet.size());
        //GanttDay ganttDayUpdated = new GanttDay(day, newAvail, team, ticketSet);
        GanttDay ganttDayUpdated = new GanttDay(keyGanttDay,newAvail,ticketSet);
        ganttDayToUpdate.update(ganttDayUpdated);
        ganttDayDao.save(ganttDayToUpdate);

        return ganttDayToUpdate;
            /*}else{
                return null;*/
    }
}