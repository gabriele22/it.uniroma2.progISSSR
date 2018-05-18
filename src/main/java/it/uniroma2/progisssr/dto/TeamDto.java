package it.uniroma2.progisssr.dto;

import java.util.ArrayList;

public class TeamDto {
    private Long ID;
    private String teamName;
    private String teamLeaderId;
    private String teamCoordinatorId;
    private ArrayList<String> teamMembersId;

    public TeamDto() {
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

    public String getTeamLeaderId() {
        return teamLeaderId;
    }

    public void setTeamLeaderId(String teamLeaderId) {
        this.teamLeaderId = teamLeaderId;
    }

    public String getTeamCoordinatorId() {
        return teamCoordinatorId;
    }

    public void setTeamCoordinatorId(String teamCoordinatorId) {
        this.teamCoordinatorId = teamCoordinatorId;
    }

    public ArrayList<String> getTeamMembersId() {
        return teamMembersId;
    }

    public void setTeamMembersId(ArrayList<String> teamMembersId) {
        this.teamMembersId = teamMembersId;
    }

    public String getTeamName() {
        return teamName;
    }
}
