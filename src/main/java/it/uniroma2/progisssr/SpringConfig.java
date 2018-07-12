package it.uniroma2.progisssr;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
//NB: avverte Spring che questa è una classe usata per la configurazione di qualche componente
@EnableScheduling
//NB: abilità lo scheduling in Spring, necessario per poter usare l'annotazione @Scheduled (vedi EscalationController)
public class SpringConfig {
}
