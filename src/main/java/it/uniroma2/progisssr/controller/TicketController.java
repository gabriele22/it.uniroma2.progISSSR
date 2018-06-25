package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.TeamDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.entity.Team;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import it.uniroma2.progisssr.utils.State;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TicketController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;
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

    public List<Ticket> addDependentTicket( @NotNull Long ID, @NotNull Long dependentID) throws EntitaNonTrovataException {
        Ticket ticketMain = ticketDao.getOne(ID);
        Ticket dependentTicket = ticketDao.getOne(dependentID);
        List<Ticket> cycle = new ArrayList<>();

        if (ticketMain == null || dependentTicket==null)
            throw new EntitaNonTrovataException();
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


    public Ticket releaseTicket(@NotNull  Long id, @NotNull Ticket ticket) throws EntitaNonTrovataException {
        Ticket ticketReleased = ticketDao.getOne(id);
        if (ticketReleased == null )
            throw new EntitaNonTrovataException();
        ticketReleased.update(ticket);
        ticketDao.save(ticketReleased);
        Set<Ticket> dependents = ticketReleased.decreaseDependents();
        for(Ticket t: dependents)
            ticketDao.save(t);
        return ticketReleased;

    }

    public Ticket addRegression(@NotNull Long id,@NotNull Long idGenerator) throws EntitaNonTrovataException {
        Ticket ticketRegression = ticketDao.getOne(id);
        Ticket ticketGenerator = ticketDao.getOne(idGenerator);
        if (ticketRegression == null )
            throw new EntitaNonTrovataException();
        if (ticketGenerator == null )
            throw new EntitaNonTrovataException();
        ticketRegression.addRegression(ticketGenerator);
        return ticketDao.save(ticketRegression);

    }

    public List<Ticket> findAllTicketsByStatusNot(String status) {
        List<Ticket> tickets = ticketDao.findByStatusNot(status);
        return tickets;

    }

    public List<Ticket> findAllTicketsForEquality(String status) {
        List<Ticket> tickets = ticketDao.findByStatusAndDependentTicketsIsNullAndRegressionTicketsGeneratorIsNull(status);
        return tickets;
    }

    public List<Ticket> findAllTicketsForDependency(String status) {
        List<Ticket> tickets = ticketDao.findByStatusAndSameTicketIsNullAndRegressionTicketsGeneratorIsNull(status);
        return tickets;
    }

    public List<Ticket> findAllTicketsForRegression(String status) {
        List<Ticket> tickets = ticketDao.findByStatusAndDependentTicketsIsNullAndSameTicketIsNull(status);
        return tickets;
    }



    //------------  Seconda versione relazioni-------------------------


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


    //per stampare la coda dei ticket in pending
    public List<Ticket> findTicketInQueue (){
        List<Ticket> tickets = ticketDao.findDistinctByStatusOrderByRankDesc(State.PENDING.toString().toLowerCase());

        return tickets;
    }


//---------------------------GANTT----------------------------------------------
    //per ottenere tutti i ticket assegnati ad un team
    public List<Ticket> findTicketByTeam(String teamName) {
        Team team = teamDao.getOne(teamName);
        List<Ticket> tickets = ticketDao.findByTeam(team);
        return  tickets;


    }

    public Integer computeDuration(String teamName, Ticket ticketUpdated) {
        Team team = teamDao.getOne(teamName);
        Double teamWeight = teamDao.findTeamWeightByTeam(team);
        Double difficulty = ticketDao.findDifficultyByTicket(ticketUpdated);
        //stima della duration in eccesso
        Integer duration = (int)Math.ceil(teamWeight*difficulty);
        ticketUpdated.updateDuration(duration);
        ticketDao.save(ticketUpdated);

        return duration;




    }

    public void putDateExecutionStart(Integer duration, Ticket ticketUpdated, String teamName) {
        List<Ticket> dependentTickets = ticketDao.getDependentTicketByTicket(ticketUpdated);
        Calendar now = Calendar.getInstance();
        String dateExec= now.get(Calendar.DATE)+ "-"+ now.get(Calendar.MONTH)+ "-"+now.get(Calendar.YEAR);
        ticketUpdated.updateDateExecutionStart(dateExec);
        now.add(Calendar.DATE, duration);
        String dateDependentTicket= now.get(Calendar.DATE)+ "-"+ now.get(Calendar.MONTH)+ "-"+now.get(Calendar.YEAR);
        if(dependentTickets.size()!=0)
            for(Ticket ticket: dependentTickets){
                ticket.updateDateExecutionStart(dateDependentTicket);
                Integer durationTickDependent = computeDuration(teamName,ticketUpdated);
                ticket.updateDuration(durationTickDependent);
                ticketDao.save(ticket);
            }

        ticketDao.save(ticketUpdated);

    }

    public List<Ticket> findTicketForGanttByTeam(String teamName) {
        Team team =  teamDao.getOne(teamName);
        List<Ticket> tickets = ticketDao.findByTeamAndStatus(team,State.EXECUTION.toString().toLowerCase());
        return tickets;

    }

    //ritorna la lista di ticket associata ad un team in base ad uno degli elementi del team
    public List<Ticket> findTicketForGanttByPerson(String person) {
        User user = userDao.getOne(person);
        HashSet<User> set = new HashSet<>();
        set.add(user);
        List<Team> teamList = teamDao.findAllByTeamMembersContainsOrTeamLeaderOrTeamCoordinator(set,user,user);
        List<Ticket> tickets= new ArrayList<>();
        for(Team team : teamList) {
            tickets.addAll(ticketDao.findByTeamAndStatus(team, State.EXECUTION.toString().toLowerCase()));

        }
        return tickets;
    }
}
