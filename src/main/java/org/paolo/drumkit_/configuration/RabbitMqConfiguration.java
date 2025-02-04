package org.paolo.drumkit_.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    //creo la coda per rabbit e la imposto durabile
    @Bean
    protected Queue getQueueC() {
        return QueueBuilder.durable("Cestino")
                .build();
    }

    //creo lo scambio per rabbit e lo imposto durabile
    @Bean
    protected Exchange getExchange() {
        return ExchangeBuilder.topicExchange("amq.topic")
                .durable(true)
                .build();
    }

    //creo il binding tra la coda e lo scambio
    @Bean
    protected Binding getBindingFra() {
        return BindingBuilder
                .bind(getQueueC())
                .to(getExchange())
                //.with("chat.private.#")
                .with("amq.topic.#")
                .noargs();
    }
    //creo il listener per la coda e lo collego alla connessione
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