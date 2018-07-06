package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.GanttDayController;
import it.uniroma2.progisssr.entity.GanttDay;
import it.uniroma2.progisssr.entity.Target;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "gantt")
@CrossOrigin
public class GanttDayRestService {
    @Autowired
    private GanttDayController ganttDayController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<GanttDay> createGanttDay(@RequestBody GanttDay ganttDay) {
        GanttDay ganttDayNew = ganttDayController.createGanttDay(ganttDay);
        return new ResponseEntity<>(ganttDayNew, HttpStatus.CREATED);
    }



    @RequestMapping(path = "createGanttInstance/{teamName}/{firstDay}/{duration}/{ticketId}", method = RequestMethod.POST)
    public ResponseEntity<List<GanttDay>> createGanttInstance(@RequestBody Ticket ticket, @PathVariable String teamName,
                                                              @PathVariable String firstDay, @PathVariable Integer duration,
                                                              @PathVariable Long ticketId) {
/*
        Ticket ticketAssigned = ganttDayController.createGanttInstance(ticket,teamName,firstDay,duration, ticketId);
        return new ResponseEntity<>(ticketAssigned,ticketAssigned == null ? HttpStatus.UNAUTHORIZED : HttpStatus.OK);*/

        List<GanttDay> ganttDays = ganttDayController.createGanttInstance(ticket,teamName,firstDay,duration, ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
