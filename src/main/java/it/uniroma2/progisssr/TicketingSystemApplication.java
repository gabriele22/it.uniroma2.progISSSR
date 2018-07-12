package it.uniroma2.progisssr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//NB: comunica a Spring che questa è un'applicazione Spring
public class TicketingSystemApplication {


    public static void main(String[] args) {

        SpringApplication.run(TicketingSystemApplication.class, args);

    }
}