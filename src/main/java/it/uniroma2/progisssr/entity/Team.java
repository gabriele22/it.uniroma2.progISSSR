package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private Utente teamLeader;
    @OneToOne
    private Utente teamCoordinator;
    @OneToMany(mappedBy = "ID")
    private Set<Utente> teamMembers;

    public Team(String teamName, Utente teamLeader, Utente teamCoordinator) {
        this.teamName = teamName;
        this.teamLeader = teamLeader;
        this.teamCoordinator = teamCoordinator;
    }
}
