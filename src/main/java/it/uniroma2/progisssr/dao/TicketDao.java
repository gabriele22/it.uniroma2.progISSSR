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

    List<Ticket> findBySameTicket(Ticket ticket);

    //@Query("select '*' from Ticket t inner join Ticket dt where dt.ID = :mainID")
    //@Query("select '*' from Ticket t join t.dependentTickets dt on mainID")
    //List<Ticket> getTicketsByIDAndDAndDependentTickets(@Param("mainID") Long ID );


}
