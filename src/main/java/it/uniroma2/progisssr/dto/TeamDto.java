package it.uniroma2.progisssr.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class TeamDto {
    private Long ID;
    private String teamName;
    private String teamLeaderUsername;
    private String teamCoordinatorUsername;
    private Set<String> teamMembersUsername;

    public TeamDto() {
        this.teamMembersUsername = new HashSet<>();
    }


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLeaderUsername() {
        return teamLeaderUsername;
    }

    public void setTeamLeaderUsername(String teamLeaderUsername) {
        this.teamLeaderUsername = teamLeaderUsername;
    }

    public String getTeamCoordinatorUsername() {
        return teamCoordinatorUsername;
    }

    public void setTeamCoordinatorUsername(String teamCoordinatorUsername) {
        this.teamCoordinatorUsername = teamCoordinatorUsername;
    }

    public Set<String> getTeamMembersUsername() {
        return teamMembersUsername;
    }

    public void setTeamMembersUsername(Set<String> teamMembersUsername) {
        this.teamMembersUsername = teamMembersUsername;
    }

    public String getTeamName() {
        return teamName;
    }
}
