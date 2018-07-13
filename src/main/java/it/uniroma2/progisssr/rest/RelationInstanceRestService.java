package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.RelationInstanceController;
import it.uniroma2.progisssr.entity.RelationInstance;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.exception.AlreadyPresentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "relationInstance")
@CrossOrigin
public class RelationInstanceRestService {

    @Autowired
    RelationInstanceController relationInstanceController;

    @RequestMapping(path = "{relationName}/{fatherId}/{sonId}", method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> createRelationInstance(@RequestBody RelationInstance relationInstance,
                                                                   @PathVariable String relationName,
                                                                   @PathVariable Long fatherId,
                                                                   @PathVariable Long sonId) {
        List<Ticket> ticketsCycle = null;
        try {
            ticketsCycle = relationInstanceController.createRelationInstance(relationInstance,
                                                                            relationName, fatherId, sonId);
        } catch (AlreadyPresentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity<>(ticketsCycle, !ticketsCycle.isEmpty() ? HttpStatus.FAILED_DEPENDENCY : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RelationInstance>  findRelationInstance(@PathVariable Long id) {
        RelationInstance relationInstance = relationInstanceController.findRelationInstanceById(id);
        return new ResponseEntity<>(relationInstance, relationInstance == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    //NB: ritorna i figli di un ticket relativi alla relazione "relationName"
    @RequestMapping(path = "/findSonTickets/{relationName}/{fatherTicketId}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>>  findSonTickets(@PathVariable String relationName, @PathVariable Long fatherTicketId) {
        List<Ticket> sonTickets = relationInstanceController.findTicketsByRelation(relationName, fatherTicketId);
        return new ResponseEntity<>(sonTickets, sonTickets == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    //NB: ritorna la lista delle relazioni non custom di cui fatherTicket è padre
    @RequestMapping(path = "/findRelations/{fatherTicketId}", method = RequestMethod.GET)
    public ResponseEntity<List<RelationInstance>>  findRelationByFatherTicket(@PathVariable Long fatherTicketId) {
        List<RelationInstance> sonTickets = relationInstanceController.findRelationsByFather(fatherTicketId);
        return new ResponseEntity<>(sonTickets, sonTickets == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

}
