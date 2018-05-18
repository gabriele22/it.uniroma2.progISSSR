package it.uniroma2.progisssr.entity;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "person")
public class User {

    private String name;
    private String surname;
    @NaturalId
    private String email;
    @Id
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
        this.tickets = new HashSet<>();
    }

    public void update(@NotNull User userUpdated) {
        if(userUpdated.name != null)
            this.name = userUpdated.name;
        if(userUpdated.surname != null)
            this.surname = userUpdated.surname;
        if(userUpdated.email != null)
            this.email = userUpdated.email;
        if (userUpdated.username != null)
            this.username = userUpdated.username;
        if (userUpdated.password!= null)
            this.password = userUpdated.password;
        if (userUpdated.role != null)
            this.role = userUpdated.role;
        if (userUpdated.tickets != null)
            if (!userUpdated.tickets.isEmpty())
                this.tickets = userUpdated.tickets;
    }

    @Override
    public String toString() {
        return this.username;
    }

    public void addTickets(@NotNull Ticket ticket) {
        this.tickets.add(ticket);
    }

    public String print() {
        return  "name: "    +   this.name       + "\n" +
                "surname:"  +   this.surname    + "\n" +
                "email: "   +   this.email      + "\n" +
                "username:" +   this.username   + "\n" +
                "password:" +   this.password   + "\n" +
                "role:"     +   this.role       + "\n" +
                "tickets:"  +   this.tickets.toString();

    }
}
