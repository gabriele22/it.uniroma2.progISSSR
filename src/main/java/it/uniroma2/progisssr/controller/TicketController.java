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
import java.util.ArrayList;
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
        Product product = null;
        User user = null;
        if(ticketDto.getProductId()!=null)
            product = productDao.getOne(ticketDto.getProductId());
        if(ticketDto.getCustomerId()!=null)
            user = userDao.getOne(ticketDto.getCustomerId());
        Ticket ticket = new Ticket(ticketDto.getStatus(), ticketDto.getDateStart(),ticketDto.getCategory(),
                ticketDto.getTitle(),ticketDto.getDescription(),
                product,ticketDto.getCustomerPriority(),user);
        return ticket;
    }


    private TicketDto unmarshalling(Ticket ticket){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(ticket, TicketDto.class);

    }


    @Transactional
    public @NotNull TicketDto updateTicket(@NotNull Long ID, @NotNull TicketDto ticketDto) throws EntitaNonTrovataException {

        Ticket ticketToUpdate = ticketDao.getOne(ID);
        if (ticketToUpdate == null)
            throw new EntitaNonTrovataException();
        ticketDto.setID(ID);
        ticketToUpdate.update(marshall(ticketDto));

        Ticket ticketUpdated = ticketDao.save(ticketToUpdate);
        return unmarshalling(ticketUpdated);
    }

    public TicketDto findTicketById(@NotNull Long id) {
        TicketDto ticketDto = unmarshalling(ticketDao.getOne(id));
        return ticketDto;
    }

    public boolean deleteTicket(@NotNull Long id) {
        if (!ticketDao.existsById(id)) {
            return false;
        }
        ticketDao.deleteById(id);
        return true;
    }

    public List<TicketDto> findAllTickets() {
        List<Ticket> tickets = ticketDao.findAll();
        List<TicketDto> ticketsDto =new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            ticketsDto.add(unmarshalling(tickets.get(i)));
        }
        return ticketsDto;
    }

/*    public static void main(String[] strings) {
        TicketController ticketController = new TicketController();
        List<Ticket> tickets = ticketController.findAllTickets();
        System.out.println(tickets);
    }*/
}
