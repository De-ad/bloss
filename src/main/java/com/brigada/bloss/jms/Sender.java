package com.brigada.bloss.jms;

import jakarta.jms.Session;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;

import java.util.HashMap;

@Component
public class Sender {

    @Value("${spring.rabbitmq.films-queue-name}")
    private String filmsQueueName;

    @Qualifier("blossJmsTemplate")
    @Autowired
    JmsTemplate template;

    public void send(HashMap<String, String> mappedObject) {

        MessageCreator messageCreator = (Session session) -> session.createObjectMessage(mappedObject);

        template.send(
                filmsQueueName,
                messageCreator
        );

    }

}
