package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.RelationController;
import it.uniroma2.progisssr.controller.RelationInstanceController;
import it.uniroma2.progisssr.entity.Relation;
import it.uniroma2.progisssr.entity.RelationInstance;
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

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<RelationInstance> createRelation(@RequestBody RelationInstance relationInstance) {
        RelationInstance newRelationInstance = relationInstanceController.createRelationInstance(relationInstance);
        return new ResponseEntity<>(newRelationInstance, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RelationInstance>  findRelation(@PathVariable Long id) {
        RelationInstance relationInstance = relationInstanceController.findRelationInstanceById(id);
        return new ResponseEntity<>(relationInstance, relationInstance == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    /*@RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<RelationInstance>> findAllRelations() {
        List<RelationInstance> relationInstances = relationInstanceController.findAll;
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }*/
}
