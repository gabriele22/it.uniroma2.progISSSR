package it.uniroma2.progisssr.rest;

import it.uniroma2.progisssr.controller.TeamController;
import it.uniroma2.progisssr.dto.TeamDto;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path =  "team")
public class TeamRestService {

    @Autowired
    private TeamController teamController;
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<TeamDto> createTeam(@RequestBody TeamDto teamDto){
        TeamDto newTeamDto = teamController.createTeam(teamDto);
        return new ResponseEntity<>(newTeamDto, HttpStatus.CREATED);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<TeamDto> updateTeam(@PathVariable Long id, @RequestBody TeamDto teamDto) {
        TeamDto teamtUpdatedDto = null;
        try {
            teamtUpdatedDto = teamController.updateTeam(id, teamDto);
        } catch (EntitaNonTrovataException e) {
            return new ResponseEntity<>(teamtUpdatedDto, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teamtUpdatedDto, HttpStatus.OK);
    }



    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<TeamDto>  findTeam(@PathVariable Long id) {
        TeamDto teamDto = teamController.findTeamById(id);
        return new ResponseEntity<>(teamDto, teamDto == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteTeam(@PathVariable Long id) {
        boolean deleted = teamController.deleteTicket(id);
        return new ResponseEntity<>(deleted, deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<TeamDto>> findAllTeams() {
        List<TeamDto> teamDto = teamController.findAllTeam();
        return new ResponseEntity<>(teamDto, HttpStatus.OK);
    }



}
