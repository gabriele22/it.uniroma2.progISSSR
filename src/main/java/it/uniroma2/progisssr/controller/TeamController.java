package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.dto.TeamDto;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.modelmapper.ModelMapper;
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
    public @NotNull TeamDto createTeam (@NotNull TeamDto teamDto){
        Team newTeam = this.marshalling(teamDto);
        teamDao.save(newTeam);
        return unmarshalling(newTeam);
    }

    private Team marshalling(TeamDto teamDto){
        User teamLeader= null;
        User teamCoordinator = null;
        Set<User> teamMembers = new HashSet<>();

        if(teamDto.getTeamLeaderUsername()!=null)
            teamLeader = userDao.getOne(teamDto.getTeamLeaderUsername());
        if(teamDto.getTeamCoordinatorUsername()!=null)
            teamCoordinator = userDao.getOne(teamDto.getTeamCoordinatorUsername());

        System.out.println("MEMBRIIIIIIIIIIIIIIIIIIIII"+teamDto.getTeamMembersUsername());
        if(teamDto.getTeamMembersUsername()!=null)
            for(String s: teamDto.getTeamMembersUsername()){

                teamMembers.add(userDao.getOne(s));
            }
        System.out.println(teamMembers);
        Team team = new Team(teamDto.getTeamName(),teamLeader,teamCoordinator,(Set<User>) teamMembers);
        System.out.println("TEAMMM  "+ unmarshalling(team).getTeamMembersUsername());
      return team;

    }

    private TeamDto unmarshalling (Team team){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(team,TeamDto.class);
    }

    @Transactional
    public @NotNull TeamDto updateTeam( @NotNull Long ID, @NotNull TeamDto teamDto) throws EntitaNonTrovataException {
        Team teamToUpdate = teamDao.getOne(ID);
        if(teamToUpdate == null)
            throw new EntitaNonTrovataException();
        teamDto.setID(ID);
        teamToUpdate.update(marshalling(teamDto));
        Team teamUpdated = teamDao.save(teamToUpdate);
        return unmarshalling(teamUpdated);

    }

    public TeamDto findTeamById(@NotNull Long id){
        TeamDto teamDto = unmarshalling(teamDao.getOne(id));
        return teamDto;
    }

    public boolean deleteTicket(@NotNull Long id){
        if(!teamDao.existsById(id)){
            return false;
        }
        teamDao.deleteById(id);
        return true;
    }

    public List<TeamDto> findAllTeam(){
        List<Team> teams = teamDao.findAll();
        List<TeamDto> teamsDto = new ArrayList<>();
        for (Team team : teams){
            teamsDto.add(unmarshalling(team));
        }
        return teamsDto;
    }

}
