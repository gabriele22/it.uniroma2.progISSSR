package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "team_ID")
    private Long ID;
    private String teamName;
    @OneToOne
    private User teamLeader;
    @OneToOne
    private User teamCoordinator;
    @OneToMany
    @JoinTable(name = "Team_Members")
    private Set<User> teamMembersUsername;

    public Team(String teamName, User teamLeader, User teamCoordinator, Set<User> teamMembers) {
        this.teamName = teamName;
        this.teamLeader = teamLeader;
        this.teamCoordinator = teamCoordinator;
        this.teamMembersUsername = teamMembers;
    }


    public void update(@NotNull Team teamUpdated) {
        if (teamUpdated.teamName != null)
            this.teamName = teamUpdated.teamName;
        if (teamUpdated.teamCoordinator != null)
            this.teamCoordinator = teamUpdated.teamCoordinator;
        if (teamUpdated.teamLeader != null)
            this.teamLeader = teamUpdated.teamLeader;
        if (teamUpdated.teamMembersUsername != null)
            this.teamMembersUsername = teamUpdated.teamMembersUsername;
    }


}
