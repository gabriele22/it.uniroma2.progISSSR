package it.uniroma2.progisssr.dao;


import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketDao extends JpaRepository<Ticket,Long> {

    /*@Modifying
    @Query("SELECT '*' FROM Ticket t where t.customer = :username")*/
    List<Ticket> findByCustomer(/*@Param("username") */User customer);

}
