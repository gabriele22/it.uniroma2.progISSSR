package it.uniroma2.progisssr.dao;


import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import org.hibernate.type.ListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TicketDao extends JpaRepository<Ticket,Long> {

    /*@Modifying
    @Query("SELECT '*' FROM Ticket t where t.customer = :username")*/
    List<Ticket> findByCustomer(/*@Param("username") */User customer);

    List<Ticket> findBySameTicket(Ticket ticket);

    List<Ticket> findByStatusNot(String status);
    //to use in select equality
    List<Ticket> findByStatusAndDependentTicketsIsNullAndRegressionTicketsGeneratorIsNull(String status);
    //to use in select dependency
    List<Ticket> findByStatusAndSameTicketIsNullAndRegressionTicketsGeneratorIsNull(String status);
    //to use in select regression
    List<Ticket> findByStatusAndDependentTicketsIsNullAndSameTicketIsNull(String status);

    //Ticket where is possible add any relation
    List<Ticket> findDistinctByStatusAndSameTicketIsNullAndDependentTicketsIsNullAndCountDependenciesIsNullAndRegressionTicketsGeneratorIsNull(String status);
    //Ticket where is possible add a dependency relation
    List<Ticket> findDistinctByStatusAndDependentTicketsIsNotNullOrCountDependenciesIsNotNull(String status);
    //Ticket with no relation
    List<Ticket> findDistinctBySameTicketIsNullAndDependentTicketsIsNullAndCountDependenciesIsNullAndRegressionTicketsGeneratorIsNullAndStatusIsNot(String status);
    //Ticket for create a equality relation
    List<Ticket> findBySameTicketIsNotNull();
    //Ticket for create a dependency relation
    List<Ticket> findDistinctByDependentTicketsIsNotNullOrCountDependenciesIsNotNull();
    //Ticket for create a regression relation
    List<Ticket> findDistinctByStatus(String status);
    List<Ticket> findDistinctByStatusOrderByRank(String status);
    List<Ticket> findDistinctBySameTicketIsNullAndDependentTicketsIsNullAndCountDependenciesIsNullAndRegressionTicketsGeneratorIsNull();

    @Query("select t.id from Ticket t where t= :ticket")
    Long getIDByTicket(@Param("ticket") Ticket ticket);




    //@Query("select '*' from Ticket t inner join Ticket dt where dt.ID = :mainID")
/*    @Query("SELECT t.dependentTickets FROM Ticket t  where t.ID = :mainID")
    Set<Ticket> getDeoendentTicketsByTicket(@Param("mainID") Long ID );*/

}
