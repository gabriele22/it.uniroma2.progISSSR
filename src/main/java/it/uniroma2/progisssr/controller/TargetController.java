package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.TargetDao;
import it.uniroma2.progisssr.entity.Target;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class TargetController {

    @Autowired
    TargetDao targetDao;

    @Transactional
    public @NotNull Target createTarget(@NotNull Target target) {
        Target newTarget = targetDao.save(target);
        return newTarget;
    }
    @Transactional
    public @NotNull Target updateTarget( @NotNull Long id, @NotNull Target target) throws EntitaNonTrovataException{

        Target targetToUpdate = targetDao.getOne(id);
        if (targetToUpdate == null)
            throw new EntitaNonTrovataException();
        targetToUpdate.update(target);
        Target targetUpdated = targetDao.save(targetToUpdate);
        return targetUpdated;

    }

    public Target findTargetById(@NotNull Long id) {
        Target target = targetDao.getOne(id);
        return target;
    }

    public boolean deleteTarget(@NotNull Long id){
        if(!targetDao.existsById(id)){
            return false;
        }
        targetDao.deleteById(id);
        return true;
    }

    public List<Target> findAllTickets(){
        List<Target> targets = targetDao.findAll();
        return targets;
    }
}
