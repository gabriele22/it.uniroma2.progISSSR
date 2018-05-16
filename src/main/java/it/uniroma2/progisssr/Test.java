package it.uniroma2.progisssr;

import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.User;

import java.util.Date;

public class Test {

    TicketController ticketController = new TicketController();
    Product product = new Product("nome",1,"wvwvv");
    User user = new User("pippo","franco","ooo@ini","pip","pip","customer");
    Date date = new Date(2012,11,11);
   // Ticket ticket = new Ticket(date,"bug")
}
