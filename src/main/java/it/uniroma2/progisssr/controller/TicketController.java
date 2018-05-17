package it.uniroma2.progisssr.controller;


import it.uniroma2.progisssr.dao.ProductDao;
import it.uniroma2.progisssr.dao.TicketDao;
import it.uniroma2.progisssr.dao.UserDao;
import it.uniroma2.progisssr.dto.TicketDto;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.Ticket;
import it.uniroma2.progisssr.entity.User;
import it.uniroma2.progisssr.exception.EntitaNonTrovataException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

// @Service identifica uno Spring Bean che nell'architettura MVC è un Controller
@Service
public class TicketController {

    @Autowired
    private TicketDao ticketDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    public @NotNull TicketDto createTicket(@NotNull TicketDto ticketDto) {

        Ticket newTicket = this.marshall(ticketDto);
        ticketDao.save(newTicket);
        return unmarshalling(newTicket);
    }

    private Ticket marshall(TicketDto ticketDto){
        Product product = productDao.getOne(ticketDto.getProductId());
        User user = userDao.getOne(ticketDto.getCustomerId());
        Ticket ticket = new Ticket(ticketDto.getDateStart(),ticketDto.getCategory(),
                ticketDto.getTitle(),ticketDto.getDescription(),
                product,ticketDto.getCustomerPriority(),user);
        return ticket;
    }


    private TicketDto unmarshalling(Ticket ticket){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ticket, TicketDto.class);

    }


    @Transactional
    public @NotNull Ticket updateTicket(@NotNull Long id, @NotNull Ticket datiAggiornati) throws EntitaNonTrovataException {
        Ticket ticketDaAggiornare = ticketDao.getOne(id);
        if (ticketDaAggiornare == null)
            throw new EntitaNonTrovataException();

        ticketDaAggiornare.aggiorna(datiAggiornati);

        Ticket ticketAggiornata = ticketDao.save(ticketDaAggiornare);
        return ticketAggiornata;
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
        return ticketDao.findAll();
    }
}
