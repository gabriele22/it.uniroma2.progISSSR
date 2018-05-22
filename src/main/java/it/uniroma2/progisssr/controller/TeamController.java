package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import java.util.*;

@Service
public class TeamController {

    @Autowired
    private TeamDao teamDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public @NotNull Team createTeam (@NotNull Team team){
        teamDao.save(team);
        return team;
    }

    @Transactional
    public @NotNull Team updateTeam( @NotNull Long ID, @NotNull Team team) throws EntitaNonTrovataException {
        Team teamToUpdate = teamDao.getOne(ID);
        if(teamToUpdate == null)
            throw new EntitaNonTrovataException();
        teamToUpdate.update(team);
        Team teamUpdated = teamDao.save(teamToUpdate);
        return teamUpdated;

    }

    public Team findTeamById(@NotNull Long id){
        Team team = teamDao.getOne(id);
        return team;
    }

    public boolean deleteTicket(@NotNull Long id){
        if(!teamDao.existsById(id)){
            return false;
        }
        teamDao.deleteById(id);
        return true;
    }

    public List<Team> findAllTeam(){
        List<Team> teams = teamDao.findAll();
        return teams;
    }
}
