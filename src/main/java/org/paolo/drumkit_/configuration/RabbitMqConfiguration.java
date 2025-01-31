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
    protected Queue getQueue() {
        return QueueBuilder.durable("Utile")
                .build();
    }

    @Bean
    protected Exchange getExchange() {
        return ExchangeBuilder.directExchange("diretto")
                .durable(true)
                .build();
    }
//    @Bean
//    protected Binding getBinding() {
//        return BindingBuilder
//                .bind(getQueue())
//                .to(getExchange())
//                .with("chat.private.#")
//                .noargs();
//    }
    @Bean
    protected Binding getBinding() {
        return BindingBuilder
                .bind(getQueue())
                .to(getExchange())
                .with("#")
                .noargs();
    }
    @Bean
    protected Binding getBindingFra() {
        return BindingBuilder
                .bind(getQueueC())
                .to(getExchange())
                .with("chat.private.#")
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




//    @Bean
//    protected MessageListenerContainer getContainer() {
//        SimpleMessageListenerContainer smlc=new SimpleMessageListenerContainer();
//        smlc.setQueues(getQueue());
//        smlc.setConnectionFactory(getConnection());
//        smlc.setMessageListener(new ListenerMessaggi());
//        return smlc;
//    }

}