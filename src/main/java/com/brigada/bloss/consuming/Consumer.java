package com.brigada.bloss.consuming;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Profile({"consume"})
@Component
@Slf4j
public class Consumer {

    @Value("${spring.rabbitmq.cinema-queue-name")
    private String queueName;

    @Bean
    public DefaultMessageListenerContainer jmsListener(
        @Qualifier("netflixConnectionFactory") ConnectionFactory connectionFactory,
        @Qualifier("netflixDestination") Destination destination
        ) {

        MessageListenerAdapter adapter = new MessageListenerAdapter(new Receiver());
        adapter.setDefaultListenerMethod("receive");

        DefaultMessageListenerContainer jmsListener = new DefaultMessageListenerContainer();
        jmsListener.setConnectionFactory(connectionFactory);
        jmsListener.setDestinationName(queueName);
        jmsListener.setPubSubDomain(false);
        jmsListener.setMessageListener(adapter);
        
        return jmsListener;
    }

    static class Receiver {
        public void receive(HashMap<String, String> receivedMap) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info(this.getClass().getName() + " received " + receivedMap);
        }
    }

}

