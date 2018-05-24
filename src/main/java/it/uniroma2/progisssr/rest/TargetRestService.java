package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.TargetController;
import it.uniroma2.progisssr.entity.Target;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "target")
@CrossOrigin
public class TargetRestService {
    @Autowired
    private TargetController TargetController;


    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Target> createTarget(@RequestBody Target Target) {
        Target newTarget = TargetController.createTarget(Target);
        return new ResponseEntity<>(newTarget, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Target> updateTarget(@PathVariable Long id, @RequestBody Target target) {
        Target targetUpdated = null;
        try {
            targetUpdated = TargetController.updateTarget(id, target);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(targetUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(targetUpdated, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Target>  findTarget(@PathVariable Long id) {
        Target target = TargetController.findTargetById(id);
        return new ResponseEntity<>(target, target == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTarget(@PathVariable Long id) {
        boolean deleted = TargetController.deleteTarget(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Target>> findAllTarget() {
        List<Target> targetsDto = TargetController.findAllTickets();
        return new ResponseEntity<>(targetsDto, HttpStatus.OK);
    }


}
