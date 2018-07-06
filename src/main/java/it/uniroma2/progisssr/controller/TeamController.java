package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.GanttDayDao;
import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import it.uniroma2.progisssr.utils.State;
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
    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private GanttDayDao ganttDayDao;

    @Transactional
    public @NotNull Team createTeam (@NotNull Team team, @NotNull String teamName){
        Team newTeam = null;
        if(!teamDao.existsById(teamName))
            newTeam=teamDao.save(team);
        return newTeam;
    }
    @Transactional
    public @NotNull Team updateTeam( @NotNull String teamName, @NotNull Team team) throws EntitaNonTrovataException {
        Team teamToUpdate = teamDao.getOne(teamName);
        if(teamToUpdate == null)
            throw new EntitaNonTrovataException();
        teamToUpdate.update(team);
        Team teamUpdated = teamDao.save(teamToUpdate);
        return teamUpdated;

    }

    public Team findTeamById(@NotNull String teamName){
        Team team = teamDao.getOne(teamName);
        return team;
    }

    public boolean deleteTicket(@NotNull String teamName){
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

    public void computeTeamWeight(String teamName) {
        Team team = teamDao.getOne(teamName);
        List<User> teamMember = teamDao.findTeamMembersByTeam(team);
        List<Ticket> ticketAssigned = ticketDao.findByTeamAndStatus(team,State.EXECUTION.toString().toLowerCase());
        Double teamWeight = (double) ticketAssigned.size() /(double)teamMember.size();
        team.updateWeight(teamWeight);
        teamDao.save(team);
    }



    public List<Team> findAllTeamByPerson(String person) {
        User user = userDao.getOne(person);
        HashSet<User> set = new HashSet<>();
        set.add(user);
        return  teamDao.findAllByTeamMembersContainsOrTeamLeaderOrTeamCoordinator(set,user,user);
    }
}
