package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.TeamController;
import it.uniroma2.progisssr.entity.Team;
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
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        Team newTeam = teamController.createTeam(team);
        return new ResponseEntity<>(newTeam, HttpStatus.CREATED);
    }


    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team team) {
        Team teamtUpdated = null;
        try {
            teamtUpdated = teamController.updateTeam(id, team);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(teamtUpdated, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamtUpdated, HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<Team>  findTeam(@PathVariable Long id) {
        Team team = teamController.findTeamById(id);
        return new ResponseEntity<>(team, team == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTeam(@PathVariable Long id) {
        boolean deleted = teamController.deleteTicket(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<Team>> findAllTeams() {
        List<Team> team = teamController.findAllTeam();
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
}
