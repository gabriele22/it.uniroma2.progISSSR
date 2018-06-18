package it.uniroma2.progisssr.controller;

import it.uniroma2.progisssr.dao.EscalationDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.entity.Escalation;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.thread.ThreadEscalation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;


/*
Classe controllore che alla creazione di Escalation avvia un runnable che controlla
i ticket in stato di pending in maniera periodica fino alla creazione di una nuova
classe Escalation
 */
@Service
public class EscalationController {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private ScheduledFuture<?> oldHandler;

    @Autowired
    private EscalationDao escalationDao;

    @Autowired
    private TicketDao ticketDao;


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






}
