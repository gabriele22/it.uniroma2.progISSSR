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
    //NB: is the same of UNIQUE in SQL attributes
    private String email;
    @Id
    private String username;
    private String password;
    private String role;



    public User(String name, String surname, String email, String username, String password, String role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
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
    }

    @Override
    public String toString() {
        return this.username;
    }

    public String print() {
        return  "name: "    +   this.name       + "\n" +
                "surname:"  +   this.surname    + "\n" +
                "email: "   +   this.email      + "\n" +
                "username:" +   this.username   + "\n" +
                "password:" +   this.password   + "\n" +
                "role:"     +   this.role       + "\n";

    }

    public boolean verifyPassword(@NotNull String password) {
        return password.equals(this.password);
    }
}
