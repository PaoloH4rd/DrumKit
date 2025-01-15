package org.paolo.drumkit_.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    protected Queue getQueue() {
        return QueueBuilder.durable("listaDurable")
                .build();
    }

    @Bean
    protected Exchange getExchange() {
        return ExchangeBuilder.topicExchange("ExchangeDurable")
                .durable(true)
                .build();
    }
}