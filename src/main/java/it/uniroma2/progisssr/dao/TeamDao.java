package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface TeamDao extends JpaRepository<Team, String> {

    @Query("select t.teamMembers from Team t where t = :team")
    List<User> findTeamMembersByTeam(@Param("team") Team team);

    @Query("select t.teamWeight from Team t where t = :team")
    Double findTeamWeightByTeam(@Param("team") Team team);

    Team findTeamByTeamMembersContainsOrTeamLeaderOrTeamCoordinator(Set<User> teamMembers, User teamLeader, User teamCoordinator);
    List<Team> findAllByTeamMembersContainsOrTeamLeaderOrTeamCoordinator(Set<User> teamMembers, User teamLeader, User teamCoordinator);


}
