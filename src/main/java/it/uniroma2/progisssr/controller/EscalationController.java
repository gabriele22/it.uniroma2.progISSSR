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
import java.util.ArrayList;
import java.util.List;

@Service
public class EscalationController {
    @Autowired
    private EscalationDao escalationDao;

    @Autowired
    private TicketDao ticketDao;


    @Transactional
    public @NotNull Escalation createEscalation(@NotNull Escalation escalation) {
        Escalation newEscalation = escalationDao.save(escalation);
        //TODO dopo aver creato una nuova escalation fermi il thread, gli assegni i parametri e lo fai ripartire

        List<Ticket> tickets = ticketDao.findDistinctByStatus("pending");
        //ThreadEscalation threadEscalation =  new ThreadEscalation(3,3,3);


        return newEscalation;
    }

}
