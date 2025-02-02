package org.paolo.drumkit_.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    protected Queue getQueueC() {
        return QueueBuilder.durable("Cestino")
                .build();
    }


    @Bean
    protected Exchange getExchange() {
        return ExchangeBuilder.topicExchange("amq.topic")
                .durable(true)
                .build();
    }

    @Bean
    protected Binding getBindingFra() {
        return BindingBuilder
                .bind(getQueueC())
                .to(getExchange())
                //.with("chat.private.#")
                .with("amq.topic.#")
                .noargs();
    }

    @Bean
    protected ConnectionFactory getConnection() {
        CachingConnectionFactory c=new CachingConnectionFactory();
        c.setAddresses("localhost");
        c.setPort(5672);
        c.setUsername("guest");
        c.setPassword("guest");
        return c;
    }


}