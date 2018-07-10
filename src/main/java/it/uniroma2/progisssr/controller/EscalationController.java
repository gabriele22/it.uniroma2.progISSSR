package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.EscalationDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.entity.Escalation;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.utils.State;
import jdk.nashorn.internal.objects.annotations.Constructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;


/*
Classe controllore che alla creazione di Escalation avvia un runnable che controlla
i ticket in stato di pending in maniera periodica fino alla creazione di una nuova
classe Escalation
 */
@Service
public class EscalationController {

    /*    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> oldHandler;*/

    private Escalation escalation;
    private int i=0;

    @Autowired
    private EscalationDao escalationDao;

    @Autowired
    private TicketDao ticketDao;

/*    public EscalationController() {
        escalationStart();
    }*/

    /*

    @Transactional
    public @NotNull Escalation createEscalation(@NotNull Escalation escalation) {
        Escalation newEscalation = escalationDao.save(escalation);

        Double customerPriority = escalationDao.getCustomerPriorityByEscalation(newEscalation);
        Double teamPriority = escalationDao.getTeamPriorityByEscalation(newEscalation);
        Double time = escalationDao.getTimeByEscalation(newEscalation);

        ThreadEscalation threadEscalation =  new ThreadEscalation(customerPriority,teamPriority,time,ticketDao);

        //cancellazione dell'handler precedente
        if(oldHandler !=null)
            oldHandler.cancel(true);

        //avvio del nuovo handler periodico con il runnable aggiornato
        ScheduledFuture<?> newHandler = scheduler.scheduleAtFixedRate(threadEscalation, 1, 5, SECONDS);

        oldHandler= newHandler;

        return newEscalation;
    }


*/

    @Transactional
    public @NotNull Escalation createEscalation(@NotNull Escalation escalation) {
        Escalation newEscalation = escalationDao.save(escalation);
        this.escalation = newEscalation;

        if(i==0) {
            escalationStart();
            i++;
        }
        return newEscalation;

    }

    //cron update rank every day every six hours
    @Scheduled(fixedDelay = 5000, initialDelay = 1000 /*cron = "0 0 1,7,13,20 * * ?"*/)
    public void escalationStart() {
        Double customerPriority = escalationDao.getCustomerPriorityByEscalation(escalation);
        Double teamPriority = escalationDao.getTeamPriorityByEscalation(escalation);
        Double time = escalationDao.getTimeByEscalation(escalation);

        if(customerPriority==null)
            customerPriority=0.0;
        if(teamPriority==null)
            teamPriority=0.0;
        if(time==null)
            time=0.0;

        List<Ticket> pendingTickets = ticketDao.findDistinctByStatus(State.PENDING.toString().toLowerCase());

        for(Ticket ticket: pendingTickets){
            Double rank = ticket.computeRank(customerPriority,teamPriority,time);
            ticket.updateRank(rank);
            ticketDao.save(ticket);

        }

    }




}
