package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.GanttDayController;
import it.uniroma2.progisssr.entity.GanttDay;
import it.uniroma2.progisssr.entity.Target;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.exception.DependeciesFoundException;
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



    //NB: crea/aggiorna un'istanza di GanttDay, andando a cercare di aggiungere una nuova pianificazione per un ticket
    // In particolare ritorna, in caso di fallimento, la lista dei giorni in cui non c'è disponibilità o avverte che ci
    // sono delle dipendenze da risolvere prima per quel ticket. In caso di successo assigna il ticket al team, ne programma
    // la data di inizio esecuzione pianificata e la durata, e aggiorna le disponibilità del team nei giorni coinvolti.
    @RequestMapping(path = "createGanttInstance/{teamName}/{firstDay}/{duration}/{ticketId}", method = RequestMethod.POST)
    public ResponseEntity<List<GanttDay>> createGanttInstance(@RequestBody Ticket ticket, @PathVariable String teamName,
                                                              @PathVariable String firstDay, @PathVariable Integer duration,
                                                              @PathVariable Long ticketId) {
        List<GanttDay> ganttDays;
        try {
            ganttDays = ganttDayController.createGanttInstance(ticket,teamName,firstDay,duration, ticketId);
        }  catch (DependeciesFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.FAILED_DEPENDENCY);
        }
        if (!ganttDays.isEmpty()) {
            return new ResponseEntity<>(ganttDays, HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(ganttDays, HttpStatus.OK);
    }

}
