package it.uniroma2.progisssr.rest;


import it.uniroma2.progisssr.controller.TeamController;
import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// @RestController e @Controller identificano uno Spring Bean che nell'architettura MVC è l'anello di congiunzione tra
// la View e il Controller (vedere l'annotazione @Service della classe PersonaController).
// La differenzatra @Controller e @RestController è che @RestController (che estende @Controller) preconfigura tutti i
// metodi per accettare in input e restituire in output delle richieste HTTP il cui payload è in formato JSON.
// Per ottenere lo stesso comportamento del @RestController, si possono utilizzare l'annotazione @Controller e
// l'annotazione @ResponseBody; quest'ultima serve appunto a denotare che un metodo (o tutti i metodi di una classe)
// restituiscono dati in formati JSON. Gli attributi "produces" e "consumes" di @RequestMapping permettono di definire
// il MimeType dei dati restituiti e ricevuti, rispettivamente. Quando input e output sono in formato JSON, l'annotazione
// @RestController è un metodo sintetico per dichiararlo e fornire a Spring la configurazione necessaria per serialzizare
// e deserializzare il JSON.
@RestController
@RequestMapping(path = "ticket")
@CrossOrigin
public class TicketRestService {

    @Autowired
    private TicketController ticketController;
    @Autowired
    private TeamController teamController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        Ticket newTicket = ticketController.createTicket(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketUpdated = null;
        try {
            ticketUpdated = ticketController.updateTicket(id, ticket);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(ticketUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketUpdated, HttpStatus.OK);
    }


    //TODO quando richiedono un ticket uguale ad un altro torni il primo che è entrato nel sistema ovvero quello salvato in same ticket
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket>  findTicket(@PathVariable Long id) {
        Ticket ticket = ticketController.findTicketById(id);
        return new ResponseEntity<>(ticket, ticket == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTicket(@PathVariable Long id) {
        boolean deleted = ticketController.deleteTicket(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findAllTickets() {
        List<Ticket> tickets = ticketController.findAllTickets();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "getTicketsByUser", method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> findTicketsById(@RequestBody User user) {
        List<Ticket> tickets = ticketController.findTicketsByCustomer(user);
        if(tickets==null || tickets.size()==0)
            return new ResponseEntity<>(tickets, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "getTicketsBySameTicket", method = RequestMethod.POST)
    public ResponseEntity<List<Ticket>> findTicketsBySameTicket(@RequestBody Ticket ticket) {
        List<Ticket> tickets = ticketController.findTicketBySameTicket(ticket);
        if(tickets==null || tickets.size()==0)
            return new ResponseEntity<>(tickets, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "addEqualityTicket/{id}/{sameTicketId}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> addEqualityTicket(@PathVariable Long id,@PathVariable Long sameTicketId, @RequestBody Ticket sameTicket) {
        Ticket ticketUpdated = null;
        if (id.equals(sameTicketId))
            return new ResponseEntity<>(ticketUpdated, HttpStatus.UNAUTHORIZED);
        try {
            ticketUpdated = ticketController.updateTicket(id, sameTicket);
        }catch (EntitaNonTrovataException e){
            return new ResponseEntity<>(ticketUpdated, HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(ticketUpdated, HttpStatus.OK);


    }

    @RequestMapping(path = "addDependentTicket/{id}/{dependentTicketID}", method = RequestMethod.POST)
  //  public ResponseEntity<List<Ticket>> addDependentTicket(@PathVariable Long id,@RequestBody Ticket ticket) {
    public ResponseEntity<List<Ticket>> addDependentTicket(@PathVariable Long id,@PathVariable Long dependentTicketID) {
        List<Ticket> cycle = new ArrayList<>();
        if (id.equals(dependentTicketID))
            return new ResponseEntity<>(cycle, HttpStatus.FAILED_DEPENDENCY);
        try {
            cycle = ticketController.addDependentTicket(id, dependentTicketID);
        }catch (EntitaNonTrovataException e){
            return new ResponseEntity<>(cycle, HttpStatus.NOT_FOUND);
        }

        if(cycle.isEmpty())
            return new ResponseEntity<>(cycle, HttpStatus.OK);
        else return new ResponseEntity<>(cycle, HttpStatus.FAILED_DEPENDENCY);

    }

    //nella richiesta inserisci json con almeno id e stato(released)
    @RequestMapping(path = "release/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> releaseTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketRelease = null;
        try {
            ticketRelease = ticketController.releaseTicket(id,ticket);
        }catch (EntitaNonTrovataException e){
            return new ResponseEntity<>(ticketRelease, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticketRelease, HttpStatus.OK);

    }

    @RequestMapping(path = "addRegression/{id}/{idGenerator}", method = RequestMethod.POST)
    public ResponseEntity<Ticket> addRegression(@PathVariable Long id,@PathVariable Long idGenerator) {
        Ticket ticketRegression = null;
        if (id.equals(idGenerator))
            return new ResponseEntity<>(ticketRegression, HttpStatus.FAILED_DEPENDENCY);
        try {
            ticketRegression = ticketController.addRegression(id, idGenerator);
        } catch (EntitaNonTrovataException e){
            return new ResponseEntity<>(ticketRegression, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(ticketRegression, HttpStatus.OK);
    }

//---------------Per stampare le tabelle nelle schermate del dispatcher--------------------------------

    @RequestMapping(path = "findAllTicketsByStatusNot/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findAllTicketsByStatusNot(@PathVariable String status) {
        List<Ticket> tickets = ticketController.findAllTicketsByStatusNot(status);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @RequestMapping(path = "findAllTicketsForEquality/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findAllTicketsForEquality(@PathVariable String status) {
        List<Ticket> tickets = ticketController.findAllTicketsForEquality(status);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "findAllTicketsForDependency/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findAllTicketsForDependency(@PathVariable String status) {
        List<Ticket> tickets = ticketController.findAllTicketsForDependency(status);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    @RequestMapping(path = "findAllTicketsForRegression/{status}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findAllTicketsForRegression(@PathVariable String status) {
        List<Ticket> tickets = ticketController.findAllTicketsForRegression(status);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


//-----------------relation new version-----------------------------

    @RequestMapping(path = "findTicketNoRelation", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketNoRelation() {
        List<Ticket> tickets = ticketController.findTicketNoRelation();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "findTicketDependency", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketDependency() {
        List<Ticket> tickets = ticketController.findTicketDependency();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "findTicketForCreateEquality", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketForCreateEquality() {
        List<Ticket> tickets = ticketController.findTicketForCreateEquality();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "findTicketForCreateDependency", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketForCreateDependency() {
        List<Ticket> tickets = ticketController.findTicketForCreateDependency();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @RequestMapping(path = "findTicketForCreateRegression", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketForCreateRegression() {
        List<Ticket> tickets = ticketController.findTicketForCreateRegression();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    //rest per ottenere i ticket in coda (pending)
    @RequestMapping(path = "findTicketInQueue", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketInQueue() {
        List<Ticket> tickets = ticketController.findTicketInQueue();
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }




//--------------GANTT---------------------------------

    //per ottenere tutti i ticket associati ad un team
    @RequestMapping(path = "findTicketByTeam/{teamName}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketByTeam(@PathVariable String teamName) {
        List<Ticket> tickets = ticketController.findTicketByTeam(teamName);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }


    //associa i ticket al team
    @RequestMapping(path = "assignmentToTeam/{ticketId}/{teamName}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> assignmentToTeam(@PathVariable Long ticketId,@PathVariable String teamName,
                                                   @RequestBody Ticket ticket) {
        Ticket ticketUpdated = null;
        try {
            ticketUpdated = ticketController.updateTicket(ticketId, ticket);

        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(ticketUpdated, HttpStatus.NOT_FOUND);
        }
        teamController.computeTeamWeight(teamName);
        Integer duration = ticketController.computeDuration(teamName,ticketUpdated);
        ticketController.putDateExecutionStart(duration, ticketUpdated,teamName);

        return new ResponseEntity<>(ticketUpdated, HttpStatus.OK);
    }


    @RequestMapping(path = "findTicketForGantt/{teamName}", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> findTicketForGantt(@PathVariable String teamName) {
        List<Ticket> tickets = ticketController.findTicketForGantt(teamName);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }



}
