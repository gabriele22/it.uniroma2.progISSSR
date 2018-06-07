package it.uniroma2.progisssr.thread;

import it.uniroma2.progisssr.entity.Ticket;

import java.util.ArrayList;

public class ThreadEscalation implements Runnable{


    private Integer customerPriority;
    private Integer teamPriority;
    private Integer time;
    private Integer maxTime = 30;
    private ArrayList<Ticket> tickets;

    public ThreadEscalation(Integer customerPriority, Integer teamPriority, Integer time, ArrayList<Ticket> tickets) {
        this.customerPriority = customerPriority;
        this.teamPriority = teamPriority;
        this.time = time;
        this.tickets = tickets;
    }

    @Override
    public void run() {
        //TODO
        //problema per fare la funzione devo accedere ai campi del ticket ma non ho i getter e setter

/*        for(Ticket ticket: tickets){
            if (ticket.getDateStart > maxTime)
        }*/

    }

    public ArrayList<Ticket> getTickets() {
        return tickets;
    }
}
