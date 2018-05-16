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
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_ID")
    private Long ID;
    private String name;
    private String surname;
    private String email;
    private String nickname;
    private String password;
    private String role;
    @OneToMany(mappedBy = "writer")
    private Set<TicketMessage> ticketMessages;



    public Utente(String name, String surname, String email, String nickname, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }
}
