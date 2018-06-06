package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.RelationInstance;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelationInstanceDao extends JpaRepository<RelationInstance,Long> {


}
