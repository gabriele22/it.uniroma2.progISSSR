package it.uniroma2.progisssr;

import it.uniroma2.progisssr.entity.Ticket;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashSet;
import java.util.Set;


public class Test {

    @Scheduled(fixedDelay = 1, initialDelay = 1)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }


}

