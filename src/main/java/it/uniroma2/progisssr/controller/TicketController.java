package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.NotFoundEntityException;
import it.uniroma2.progisssr.utils.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

//NB: @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TicketController {
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private TicketDao ticketDao;

    @Transactional
    //NB: @Transactional indica a Spring che il seguente metodo è una transazione
    public @NotNull Ticket createTicket(@NotNull Ticket ticket) {
        Ticket newTicket = ticketDao.save(ticket);
        return newTicket;
    }

    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long ID, @NotNull Ticket ticket) throws NotFoundEntityException {

        Ticket ticketToUpdate = ticketDao.getOne(ID);
        if (ticketToUpdate == null)
            throw new NotFoundEntityException();
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

    @Transactional
    public List<Ticket> addDependentTicket( @NotNull Long ID, @NotNull Long dependentID) throws NotFoundEntityException {
        Ticket ticketMain = ticketDao.getOne(ID);
        Ticket dependentTicket = ticketDao.getOne(dependentID);
        List<Ticket> cycle = new ArrayList<>();

        if (ticketMain == null || dependentTicket==null)
            throw new NotFoundEntityException();
        if(ticketMain.isAlreadyDependent(dependentTicket))
            return cycle;
        //check if there is no-cycle
        if(dependentTicket.isAcycle(ticketMain, cycle).isEmpty()) {
            ticketMain.addDependentTickets(dependentTicket);
            ticketDao.save(ticketMain);
            dependentTicket.addCount();
            ticketDao.save(dependentTicket);
            return cycle;

        }
        else return cycle;
    }

    @Transactional
    public Ticket releaseTicket(@NotNull  Long id, @NotNull Ticket ticket) throws NotFoundEntityException {
        Ticket ticketReleased = ticketDao.getOne(id);
        if (ticketReleased == null )
            throw new NotFoundEntityException();
        ticketReleased.update(ticket);
        ticketDao.save(ticketReleased);
        Set<Ticket> dependents = ticketReleased.decreaseDependents();
        for(Ticket t: dependents)
            ticketDao.save(t);
        return ticketReleased;

    }

    @Transactional
    public Ticket addRegression(@NotNull Long id,@NotNull Long idGenerator) throws NotFoundEntityException {
        Ticket ticketRegression = ticketDao.getOne(id);
        Ticket ticketGenerator = ticketDao.getOne(idGenerator);
        if (ticketRegression == null )
            throw new NotFoundEntityException();
        if (ticketGenerator == null )
            throw new NotFoundEntityException();
        ticketRegression.addRegression(ticketGenerator);
        return ticketDao.save(ticketRegression);

    }

//------------  Relation  -------------------------


    public List<Ticket> findTicketNoRelation() {
        List<Ticket> tickets = ticketDao.findDistinctBySameTicketIsNullAndDependentTicketsIsNullAndCountDependenciesIsNullAndRegressionTicketsGeneratorIsNull();
        return tickets;
    }

    public List<Ticket> findTicketDependency() {
        List<Ticket> tickets = ticketDao.findDistinctByDependentTicketsIsNotNullOrCountDependenciesIsNotNull();
        return tickets;
    }

    public List<Ticket> findTicketForCreateEquality() {
        List<Ticket> tickets =ticketDao.findDistinctBySameTicketIsNull();
        return tickets;
    }

    public List<Ticket> findTicketForCreateDependency() {
        List<Ticket> tickets = ticketDao.findDistinctBySameTicketIsNullAndRegressionTicketsGeneratorIsNull();
        return tickets;
    }

    public List<Ticket> findTicketForCreateRegression() {
        List<Ticket> tickets = ticketDao.findDistinctByStatus(State.CLOSED.toString().toLowerCase());

        return tickets;
    }

//-------------- Escalation ----------------------------------------------

    //NB: per stampare la coda dei ticket in pending
    public List<Ticket> findTicketInQueue (){
        List<Ticket> tickets = ticketDao.findDistinctByStatusOrderByRankDesc(State.PENDING.toString().toLowerCase());

        return tickets;
    }


//---------------------------GANTT----------------------------------------------

    //NB: per ottenere tutti i ticket assegnati ad un team
    public List<Ticket> findTicketByTeam(String teamName) {
        Team team = teamDao.getOne(teamName);
        List<Ticket> tickets = ticketDao.findByTeamAndStatusIsNotAndStatusIsNot(team,
                State.RELEASED.toString().toLowerCase(), State.CLOSED.toString().toLowerCase());
        return  tickets;


    }

    public List<Ticket> findTicketForGanttByTeam(String teamName) {
        Team team =  teamDao.getOne(teamName);
        List<Ticket> tickets = ticketDao.findByTeamAndStatusIsNotAndStatusIsNot(team,
                State.RELEASED.toString().toLowerCase(), State.CLOSED.toString().toLowerCase());
        return tickets;

    }


    public List<Ticket> findFatherTicket(@NotNull Long ticketId) {
        Set<Ticket> tickets = new HashSet<>();
        tickets.add(ticketDao.getOne(ticketId));
        return ticketDao.findDistinctByDependentTicketsContains(tickets);
    }
}
