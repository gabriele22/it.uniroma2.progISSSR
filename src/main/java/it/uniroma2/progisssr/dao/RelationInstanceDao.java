package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Relation;
import it.uniroma2.progisssr.entity.RelationInstance;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationInstanceDao extends JpaRepository<RelationInstance,Long> {

    @Query ("select r.sonTicket from RelationInstance r where r.relation = :relation and r.fatherTicket = :fatherTicket ")
    List<Ticket> findSonTicketsByRelationAndFatherTicket(@Param("relation") Relation relation, @Param("fatherTicket") Ticket fatherTicket);

/*
    @Query ("select r from RelationInstance r where r.fatherTicket = :fatherTicket ")
    List<RelationInstance> findSonTicketsByFatherTicket( @Param("fatherTicket") Ticket fatherTicket);
*/

    List<RelationInstance> findAllRelationInstanceByFatherTicketID(Long fatherTicketId);




}