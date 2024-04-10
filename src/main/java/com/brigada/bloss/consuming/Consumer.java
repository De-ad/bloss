package com.brigada.bloss.consuming;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;


@Profile({"consume"})
@Component
public class Consumer {

    @Value("${spring.rabbitmq.cinema-queue-name}")
    private String queueName;

    @Bean("delegate")
    public MapReceiverJms delegate() {
        return new MapReceiverJms();
    }

    @Bean("netflixMessageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter(@Qualifier("delegate") MapReceiverJms delegate) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(delegate);
        adapter.setDefaultListenerMethod("receive");
        return adapter;
    }

    @Bean
    public DefaultMessageListenerContainer jmsListener(
        @Qualifier("netflixConnectionFactory") ConnectionFactory connectionFactory,
        @Qualifier("netflixMessageListenerAdapter") MessageListenerAdapter messageListenerAdapter
        ) {

        DefaultMessageListenerContainer jmsListener = new DefaultMessageListenerContainer();
        jmsListener.setConnectionFactory(connectionFactory);
        jmsListener.setDestinationName(queueName);
        jmsListener.setPubSubDomain(false);
        jmsListener.setMessageListener(messageListenerAdapter);
        
        return jmsListener;
    }

}
