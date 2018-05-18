package it.uniroma2.progisssr.dto;

import java.util.ArrayList;

public class TeamDto {
    private Long ID;
    private String teamName;
    private Long teamLeaderId;
    private Long teamCoordinatorId;
    private ArrayList<Long> teamMembersId;

    public TeamDto(){}


    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getTeamLeaderId() {
        return teamLeaderId;
    }

    public void setTeamLeaderId(Long teamLeaderId) {
        this.teamLeaderId = teamLeaderId;
    }

    public Long getTeamCoordinatorId() {
        return teamCoordinatorId;
    }

    public void setTeamCoordinatorId(Long teamCoordinatorId) {
        this.teamCoordinatorId = teamCoordinatorId;
    }

    public ArrayList<Long> getTeamMembersId() {
        return teamMembersId;
    }

    public void setTeamMembersId(ArrayList<Long> teamMembersId) {
        this.teamMembersId = teamMembersId;
    }
}
