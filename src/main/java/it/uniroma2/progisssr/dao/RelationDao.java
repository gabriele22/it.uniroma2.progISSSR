package it.uniroma2.progisssr.dao;

import it.uniroma2.progisssr.entity.Relation;
import it.uniroma2.progisssr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationDao extends JpaRepository<Relation,String> {

    @Query("select r.cyclic from Relation r where r = :relation ")
    Boolean findCyclicByRelation(@Param("relation") Relation relation);


}
