package it.uniroma2.progisssr.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    private Date dateTime; // dateStart e dateEnd
    private String subject;
    private String message;
    private String status;
    private Integer priority;
    @OneToOne
    private Lifecycle state;
    @OneToOne
    private User source;
    private String object;
    private String description;
    @Transient
    private List<String> attachedFiles;
    private String category;
    @OneToOne
    private SoftwareProduct product;
    private Integer custumerPriority;
    private Integer teamPriority;
    private String visibility;
    @OneToOne
    private User assistant;

    private String nome;
    private String cognome;




    public void aggiorna(@NotNull Ticket datiAggiornati) {
        this.nome = datiAggiornati.nome;
        this.cognome = datiAggiornati.cognome;
    }
}
