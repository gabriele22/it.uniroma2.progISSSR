package it.uniroma2.progisssr.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TicketMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "message_ID")
    private Long ID;
 /*   @ManyToOne@JoinColumn(name = "ticket_ID")
    private Ticket ticket;
    @ManyToOne@JoinColumn(name = "utente_ID")
    private User writer;*/
    private String text;
    private Date date;


}
