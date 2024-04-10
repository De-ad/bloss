package com.brigada.bloss.config;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

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

    @Bean("amazonVideoConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() {
        RMQConnectionFactory rmqcf = new RMQConnectionFactory();
        rmqcf.setUsername(rmqUsername);
        rmqcf.setPassword(rmqPassword);
        rmqcf.setVirtualHost(rmqVirtualHost);
        rmqcf.setHost(rmqHost);
        rmqcf.setPort(rmqPort);
        return rmqcf;
    }

    @Bean("amazonVideoDestination")
    public Destination jmsDestination() {
        RMQDestination jmsDestination = new RMQDestination();
        jmsDestination.setDestinationName(queueName);
        jmsDestination.setAmqp(true);
        jmsDestination.setAmqpQueueName(queueName);
        return jmsDestination;
    }
    
}