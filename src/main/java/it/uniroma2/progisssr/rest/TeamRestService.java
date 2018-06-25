package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.TeamController;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path =  "team")
@CrossOrigin
public class TeamRestService {

    @Autowired
    private TeamController teamController;
    @RequestMapping(path = "{teamName}", method = RequestMethod.POST)
    public ResponseEntity<Team> createTeam(@RequestBody Team team,@PathVariable String teamName){
        Team newTeam = teamController.createTeam(team,teamName);
        return new ResponseEntity<>(newTeam, newTeam==null? HttpStatus.FOUND: HttpStatus.CREATED);
    }


    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateTeam(@PathVariable String id, @RequestBody Team team) {
        Team teamtUpdated = null;
        try {
            teamtUpdated = teamController.updateTeam(id, team);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(teamtUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamtUpdated, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Team>  findTeam(@PathVariable String id) {
        Team team = teamController.findTeamById(id);
        return new ResponseEntity<>(team, team == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTeam(@PathVariable String id) {
        boolean deleted = teamController.deleteTicket(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAllTeams() {
        List<Team> team = teamController.findAllTeam();
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    //metodo che ritorna tutti i team associati ad una determinata persona
    @RequestMapping(path = "findAllTeamsByPerson/{person}", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAllTeamsByPerson(@PathVariable String person) {
        List<Team> team = teamController.findAllTeamByPerson(person);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

}
