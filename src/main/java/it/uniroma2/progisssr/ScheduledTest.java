package it.uniroma2.progisssr;

import org.springframework.scheduling.annotation.Scheduled;

//NB: classe test per l'annotazione @Scheduled
public class ScheduledTest {

    @Scheduled(fixedDelay = 1, initialDelay = 1)
    public void scheduleFixedRateTask() {
        System.out.println(
                "Fixed rate task - " + System.currentTimeMillis() / 1000);
    }


}

