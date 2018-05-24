package it.uniroma2.progisssr.rest;


import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
