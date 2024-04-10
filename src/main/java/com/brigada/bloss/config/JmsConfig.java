package com.brigada.bloss.config;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class JmsConfig {

    @Value("${spring.rabbitmq.username}")
    private String rmqUsername;
    @Value("${spring.rabbitmq.password}")
    private String rmqPassword;
    @Value("${spring.rabbitmq.virtual-host}")
    private String rmqVirtualHost;
    @Value("${spring.rabbitmq.host}")
    private String rmqHost;
    @Value("${spring.rabbitmq.port}")
    private Integer rmqPort;
    @Value("${spring.rabbitmq.cinema-queue-name}")
    private String queueName;

    @Bean("blossConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() {
        RMQConnectionFactory rmqcf = new RMQConnectionFactory();
        rmqcf.setUsername(rmqUsername);
        rmqcf.setPassword(rmqPassword);
        rmqcf.setVirtualHost(rmqVirtualHost);
        rmqcf.setHost(rmqHost);
        rmqcf.setPort(rmqPort);
        return rmqcf;
    }

    @Bean("blossJmsTemplate")
    public JmsTemplate jmsTemplate(@Qualifier("blossConnectionFactory") ConnectionFactory jmsConnectionFactory) {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(jmsConnectionFactory);
        template.setPubSubDomain(false); // false for a Queue, true for a Topic
        return template;

    }

    @Bean("blossDestination")
    public Destination jmsDestination() {
        RMQDestination jmsDestination = new RMQDestination();
        jmsDestination.setDestinationName(queueName);
        return jmsDestination;
    }
    
}