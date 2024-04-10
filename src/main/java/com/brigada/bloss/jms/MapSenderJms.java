package com.brigada.bloss.jms;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import jakarta.jms.Session;

@Component
public class MapSenderJms {

    @Value("${spring.rabbitmq.netflix-cinema-queue-name}")
    private String netflixQueueName;
    @Value("${spring.rabbitmq.amazon-video-cinema-queue-name}")
    private String amazonVideoQueueName;

    @Qualifier("blossJmsTemplate")
    @Autowired
    JmsTemplate template;
    
    public void send(HashMap<String, String> mappedObject) {

        MessageCreator messageCreator = (Session session) -> session.createObjectMessage(mappedObject);

        template.send(
                netflixQueueName,
                messageCreator
        );

        template.send(
                amazonVideoQueueName,
                messageCreator
        );

    }

}
