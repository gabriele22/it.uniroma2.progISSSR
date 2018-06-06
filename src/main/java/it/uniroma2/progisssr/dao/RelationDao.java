package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Relation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelationDao extends JpaRepository<Relation,String> {
}
