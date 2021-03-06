package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.NotFoundEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import java.util.*;

@Service
public class TeamController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private TeamDao teamDao;

    @Transactional
    public @NotNull Team createTeam (@NotNull Team team, @NotNull String teamName){
        Team newTeam = null;
        if(!teamDao.existsById(teamName))
            newTeam=teamDao.save(team);
        return newTeam;
    }
    @Transactional
    public @NotNull Team updateTeam( @NotNull String teamName, @NotNull Team team) throws NotFoundEntityException {
        Team teamToUpdate = teamDao.getOne(teamName);
        if(teamToUpdate == null)
            throw new NotFoundEntityException();
        teamToUpdate.update(team);
        Team teamUpdated = teamDao.save(teamToUpdate);
        return teamUpdated;

    }

    public Team findTeamById(@NotNull String teamName){
        Team team = teamDao.getOne(teamName);
        return team;
    }

    @Transactional
    public boolean deleteTeam(@NotNull String teamName){
        if(!teamDao.existsById(teamName)){
            return false;
        }
        teamDao.deleteById(teamName);
        return true;
    }

    public List<Team> findAllTeam(){
        List<Team> teams = teamDao.findAll();
        return teams;
    }

    public List<Team> findAllTeamByPerson(String person) {
        User user = userDao.getOne(person);
        HashSet<User> set = new HashSet<>();
        set.add(user);
        return  teamDao.findAllByTeamMembersContainsOrTeamLeaderOrTeamCoordinator(set,user,user);
    }
}
