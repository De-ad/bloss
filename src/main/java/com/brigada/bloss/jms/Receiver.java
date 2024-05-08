package com.brigada.bloss.jms;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

//@Profile({"consume"})
@Component
public class Receiver {

    @Value("${spring.rabbitmq.films-queue-name}")
    private String queueName;

    @Bean("delegate")
    public ReceiverImpl delegate() {
        return new ReceiverImpl();
    }

    @Bean("filmsMessageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter(@Qualifier("delegate") ReceiverImpl delegate) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(delegate);
        adapter.setDefaultListenerMethod("receive");
        return adapter;
    }

    @Bean
    public DefaultMessageListenerContainer jmsListener(
            @Qualifier("blossConnectionFactory") ConnectionFactory connectionFactory,
            @Qualifier("filmsMessageListenerAdapter") MessageListenerAdapter messageListenerAdapter
    ) {

        DefaultMessageListenerContainer jmsListener = new DefaultMessageListenerContainer();
        jmsListener.setConnectionFactory(connectionFactory);
        jmsListener.setDestinationName(queueName);
        jmsListener.setPubSubDomain(false);
        jmsListener.setMessageListener(messageListenerAdapter);

        return jmsListener;
    }

}
