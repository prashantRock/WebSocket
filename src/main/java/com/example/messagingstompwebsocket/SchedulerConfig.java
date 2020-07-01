package com.example.messagingstompwebsocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    SimpMessagingTemplate  simpMessagingTemplate;

    @Scheduled(fixedDelay = 3000)
    public void adHoc(){
        simpMessagingTemplate.convertAndSend("/topic/greetings", new Greeting("Hello Prashant !"));
    }
}
