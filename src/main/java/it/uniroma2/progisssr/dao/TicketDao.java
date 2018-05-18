package it.uniroma2.progisssr.dao;


import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDao extends JpaRepository<Ticket,Long> {

}
