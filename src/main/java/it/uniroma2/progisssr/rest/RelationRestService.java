package it.uniroma2.progisssr.rest;


import it.uniroma2.progisssr.controller.RelationController;
import it.uniroma2.progisssr.entity.Relation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "relation")
@CrossOrigin
public class RelationRestService {

    @Autowired
    RelationController relationController;

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Relation> createRelation(@RequestBody Relation relation) {
        Relation newRelation = relationController.createRelation(relation);
        return new ResponseEntity<>(newRelation, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Relation>  findRelation(@PathVariable String id) {
        Relation relation = relationController.findRelationById(id);
        return new ResponseEntity<>(relation, relation == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Relation>> findAllRelations() {
        List<Relation> relations = relationController.findAllRelations();
        return new ResponseEntity<>(relations, HttpStatus.OK);
    }

}
