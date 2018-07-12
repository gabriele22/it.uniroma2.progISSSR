package it.uniroma2.progisssr.dao;


import it.uniroma2.progisssr.entity.Team;
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

    //NB: nel DAO è possibile creare dei metodi di ricerca della classe associata al DAO (Ticket in questo caso)
    // sulla base di condizioni definibili sugli attributi (=, !=, isNull, isNotNull, Contains, etc.)
    List<Ticket> findByCustomer(User customer);

    List<Ticket> findBySameTicket(Ticket ticket);

    //Ticket for create a dependency relation
    List<Ticket> findDistinctByDependentTicketsIsNotNullOrCountDependenciesIsNotNull();
    //Ticket for create a regression relation
    List<Ticket> findDistinctByStatus(String status);
    List<Ticket> findDistinctByStatusOrderByRankDesc(String status);
    List<Ticket> findDistinctBySameTicketIsNullAndDependentTicketsIsNullAndCountDependenciesIsNullAndRegressionTicketsGeneratorIsNull();

    @Query("select t.id from Ticket t where t= :ticket")
    //NB: @Query permette di specificare una query in JPQL (query SQL sulle classi java). E' possibile specificare dei parametri
    // di input della query con ":parametro", e richiamati nell'input del metodo del DAO con la notazione @Param("parametro") ClasseParametro nomeParametro
    Long getIDByTicket(@Param("ticket") Ticket ticket);

    List<Ticket> findDistinctBySameTicketIsNull();

    List<Ticket> findDistinctBySameTicketIsNullAndRegressionTicketsGeneratorIsNull();


//-----------------------------GANTT-------------------------------
    List<Ticket> findByTeamAndStatusIsNotAndStatusIsNot(Team team, String status, String status2);

    @Query("select t.difficulty from Ticket t where t = :ticket")
    Double findDifficultyByTicket(@Param("ticket") Ticket ticket);

    @Query("select t.dependentTickets from Ticket t where  t = :ticket")
    List<Ticket> getDependentTicketByTicket(@Param("ticket") Ticket ticket);

    List<Ticket> findDistinctByDependentTicketsContains(Set<Ticket> dependentTickets);

    @Query("select t.durationEstimation from Ticket t where t = :ticket")
    Integer findDurationByTicket(@Param("ticket") Ticket ticket);

    @Query("select t.dateExecutionStart from Ticket t where t = :ticket")
    String findDateExecutionByTicket(@Param("ticket") Ticket ticket);

}
