package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.RelationInstanceDao;
import it.uniroma2.progisssr.entity.Relation;
import it.uniroma2.progisssr.entity.RelationInstance;
import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class RelationInstanceController {

    @Autowired
    RelationInstanceDao relationInstanceDao;


    @Transactional
    public @NotNull RelationInstance createRelationInstance(@NotNull RelationInstance relationInstance) {
        RelationInstance newRelation = relationInstanceDao.save(relationInstance);
        return newRelation;
    }

    public RelationInstance findRelationInstanceById(@NotNull Long id) {
        RelationInstance relation = relationInstanceDao.getOne(id);
        return relation;
    }

    public List<Ticket> findTicketsByRelation(@NotNull String relationName) {
        //List<Ticket> tickets = relationInstanceDao.
        return null;
    }
}
