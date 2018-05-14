package it.uniroma2.progisssr.rest;


import it.uniroma2.progisssr.controller.PersonaController;
import it.uniroma2.progisssr.entity.Ticket;
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
@RequestMapping(path = "person")
public class PersonaRestService {

    @Autowired
    private PersonaController personaController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Ticket> creaPersona(@RequestBody Ticket ticket) {
        Ticket ticketCreata = personaController.creaPersona(ticket);
        return new ResponseEntity<>(ticketCreata, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ticket> aggiornaPersona(@PathVariable Long id, @RequestBody Ticket ticket) {
        Ticket ticketAggiornata;
        try {
            ticketAggiornata = personaController.aggiornaPersona(id, ticket);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(ticket, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ticketAggiornata, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> cercaPersona(@PathVariable Long id) {
        Ticket ticketTrovata = personaController.cercaPersonaPerId(id);
        return new ResponseEntity<>(ticketTrovata, ticketTrovata == null ? HttpStatus.NOT_FOUND : HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> eliminaPersona(@PathVariable Long id) {
        boolean eliminata = personaController.eliminaPersona(id);
        return new ResponseEntity<>(eliminata, eliminata ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> prelevaPersone() {
        List<Ticket> persone = personaController.prelevaPersone();
        return new ResponseEntity<>(persone, HttpStatus.OK);
    }
}
