package it.uniroma2.progisssr.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String role;
    private Set<String> tickets;

    public UserDto() { this.tickets = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<String> getTickets() {
        return tickets;
    }

    public void setTickets(Set<String> tickets) {
        this.tickets = tickets;
    }
}
