package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.entity.Target;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class TargetController {

    @Autowired
    ProductDao productDao;

    @Transactional
    public @NotNull Target createTarget(@NotNull Target target) {
        Target newTarget = productDao.save(target);
        return newTarget;
    }

    @Transactional
    public @NotNull Target updateTarget( @NotNull Long id, @NotNull Target target) throws EntitaNonTrovataException{

        Target targetToUpdate = productDao.getOne(id);
        if (targetToUpdate == null)
            throw new EntitaNonTrovataException();
        targetToUpdate.update(target);
        Target targetUpdated = productDao.save(targetToUpdate);
        return targetUpdated;

    }

    public Target findTargetById(@NotNull Long id) {
        Target target = productDao.getOne(id);
        return target;
    }

    public boolean deleteTarget(@NotNull Long id){
        if(!productDao.existsById(id)){
            return false;
        }
        productDao.deleteById(id);
        return true;
    }

    public List<Target> findAllTickets(){
        List<Target> targets = productDao.findAll();
        return targets;
    }
}
