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
@Table(name = "person")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_ID")
    private Long ID;
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String role;
    @OneToMany
    private Set<Ticket> tickets;
/*    @OneToMany(mappedBy = "writer")
    private Set<TicketMessage> ticketMessages;*/



    public User(String name, String surname, String email, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}
