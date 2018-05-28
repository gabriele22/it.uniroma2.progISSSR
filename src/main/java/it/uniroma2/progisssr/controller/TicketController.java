package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;

    @Transactional
    public @NotNull Ticket createTicket(@NotNull Ticket ticket) {
        Ticket newTicket = ticketDao.save(ticket);
        return newTicket;
    }

    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long ID, @NotNull Ticket ticket) throws EntitaNonTrovataException {

        Ticket ticketToUpdate = ticketDao.getOne(ID);
        if (ticketToUpdate == null)
            throw new EntitaNonTrovataException();
        ticketToUpdate.update(ticket);

        Ticket ticketUpdated = ticketDao.save(ticketToUpdate);
        return ticketUpdated;
    }

    public Ticket findTicketById(@NotNull Long id) {
        Ticket ticket = ticketDao.getOne(id);
        return ticket;
    }

    public boolean deleteTicket(@NotNull Long id) {
        if (!ticketDao.existsById(id)) {
            return false;
        }

        ticketDao.deleteById(id);
        return true;
    }

    public List<Ticket> findAllTickets() {
        List<Ticket> tickets = ticketDao.findAll();
        return tickets;
    }

    public List<Ticket> findTicketsByCustomer(User user){
        List<Ticket> tickets = ticketDao.findByCustomer(user);
        return tickets;
    }

    public List<Ticket> findTicketBySameTicket(Ticket ticket){
        List<Ticket> tickets = ticketDao.findBySameTicket(ticket);
        return tickets;
    }

    public Boolean addDependentTicket( @NotNull Long ID, @NotNull Long dependentID) throws EntitaNonTrovataException {
        Ticket ticketMain = ticketDao.getOne(ID);
        Ticket dependentTicket = ticketDao.getOne(dependentID);

        if (ticketMain == null || dependentTicket==null)
            throw new EntitaNonTrovataException();
        if(ticketMain.isAlreadyDependent(dependentTicket))
            return true;
        //check if there is no-cycle
        if(dependentTicket.isAcycle(ticketMain)) {
            ticketMain.addDependentTickets(dependentTicket);
            ticketDao.save(ticketMain);
            dependentTicket.addCount();
            ticketDao.save(dependentTicket);
            return true;

        }
        else return false;
    }


    public Ticket releaseTicket(@NotNull  Long id, @NotNull Ticket ticket) throws EntitaNonTrovataException {
        Ticket ticketReleased = ticketDao.getOne(id);
        if (ticket == null )
            throw new EntitaNonTrovataException();
        ticketReleased.update(ticket);
        ticketDao.save(ticketReleased);
        Set<Ticket> dependents = ticketReleased.decreaseDependents();
        for(Ticket t: dependents)
            ticketDao.save(t);
        return ticketReleased;

    }
}
