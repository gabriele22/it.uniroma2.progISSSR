package it.uniroma2.progisssr;

import it.uniroma2.progisssr.controller.ProductController;
import it.uniroma2.progisssr.controller.TicketController;
import it.uniroma2.progisssr.controller.UserController;
import it.uniroma2.progisssr.entity.Product;
import it.uniroma2.progisssr.entity.User;

import java.util.Date;

public class Test {


    public static void main(String[] strings){
        TicketController ticketController = new TicketController();
        Product product = new Product("nome",1,"wvwvv");
        User user = new User("pippo","franco","ooo@ini","pip","pip","customer");
        UserController userController = new UserController();
        ProductController productController =  new ProductController();
        productController.createProduct(product);
        userController.createUser(user);


    }
   // Ticket ticket = new Ticket(date,"bug")
}
