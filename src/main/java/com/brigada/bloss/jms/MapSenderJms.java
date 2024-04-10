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

    @Value("${spring.rabbitmq.cinema-queue-name}")
    private String queueName;

    @Qualifier("blossJmsTemplate")
    @Autowired
    JmsTemplate template;
    
    public void send(HashMap<String, String> mappedObject) {

        MessageCreator messageCreator = (Session session) -> session.createObjectMessage(mappedObject);

        template.send(
                queueName,
                messageCreator
        );

    }

}
