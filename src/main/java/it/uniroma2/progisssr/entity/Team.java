package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Table(name = "team")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Team {

    @Id
    private String teamName;
    @OneToOne
    private User teamLeader;
    @OneToOne
    private User teamCoordinator;
    @OneToMany
    @JoinTable(name = "team_members")
    private Set<User> teamMembers;
    //carico del team
    private Double teamWeight;

    public Team(User teamLeader, User teamCoordinator, Set<User> teamMembers) {
        this.teamLeader = teamLeader;
        this.teamCoordinator = teamCoordinator;
        this.teamMembers = teamMembers;
        this.teamWeight=0.0;
    }

    public void update(@NotNull Team teamUpdated) {
        if (teamUpdated.teamCoordinator != null)
            this.teamCoordinator = teamUpdated.teamCoordinator;
        if (teamUpdated.teamLeader != null)
            this.teamLeader = teamUpdated.teamLeader;
        if (teamUpdated.teamMembers != null)
            this.teamMembers = teamUpdated.teamMembers;
        if(teamUpdated.teamWeight != null)
            this.teamWeight = teamUpdated.teamWeight;
    }

    public void updateWeight(Double weight){
        this.teamWeight=weight;
    }


}
