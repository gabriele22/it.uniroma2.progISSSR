package it.uniroma2.progisssr.rest;


import it.uniroma2.progisssr.controller.EscalationController;
import it.uniroma2.progisssr.entity.Escalation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "escalation")
@CrossOrigin
public class EscalationRestService {

    @Autowired
    EscalationController escalationController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Escalation> createEscalation(@RequestBody Escalation escalation) {
        Escalation newEscalation = escalationController.createEscalation(escalation);
        return new ResponseEntity<>(newEscalation, HttpStatus.CREATED);
    }
}
