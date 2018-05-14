package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.PersonaDao;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class PersonaController {

    @Autowired
    private PersonaDao personaDao;

    @Transactional
    public @NotNull Ticket creaPersona(@NotNull Ticket ticket) {
        Ticket ticketSalvato = personaDao.save(ticket);
        return ticketSalvato;
    }

    @Transactional
    public @NotNull Ticket aggiornaPersona(@NotNull Long id, @NotNull Ticket datiAggiornati) throws EntitaNonTrovataException {
        Ticket ticketDaAggiornare = personaDao.getOne(id);
        if (ticketDaAggiornare == null)
            throw new EntitaNonTrovataException();

        ticketDaAggiornare.aggiorna(datiAggiornati);

        Ticket ticketAggiornata = personaDao.save(ticketDaAggiornare);
        return ticketAggiornata;
    }

    public Ticket cercaPersonaPerId(@NotNull Long id) {
        Ticket ticketTrovata = personaDao.getOne(id);
        return ticketTrovata;
    }

    public boolean eliminaPersona(@NotNull Long id) {
        if (!personaDao.existsById(id)) {
            return false;
        }

        personaDao.deleteById(id);
        return true;
    }

    public List<Ticket> prelevaPersone() {
        return personaDao.findAll();
    }
}
