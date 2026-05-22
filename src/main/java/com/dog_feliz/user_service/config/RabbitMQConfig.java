package com.dog_feliz.user_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("!test")
public class RabbitMQConfig {

    public static final String MQ_MESSAGE_CONVERTER = "mq-message-converter";
    public static final String NOTIFICATION_EXCHANGE = "notification.exchange";
    public static final String NOTIFICATION_SCHEDULED_QUEUE = "notification.scheduled.queue";
    public static final String NOTIFICATION_SCHEDULED_ROUTING_KEY = "notification.scheduled";
    public static final String NOTIFICATION_INSTANT_QUEUE = "notification.instant.queue";
    public static final String NOTIFICATION_INSTANT_ROUTING_KEY = "notification.instant";

    @Bean(MQ_MESSAGE_CONVERTER)
    public MessageConverter mqMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            @Qualifier(MQ_MESSAGE_CONVERTER) MessageConverter mqMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(mqMessageConverter);
        return factory;
    }

    @Bean
    public DirectExchange notificationExchange() {
        return new DirectExchange(NOTIFICATION_EXCHANGE);
    }

    @Bean(NOTIFICATION_SCHEDULED_QUEUE)
    public Queue notificationScheduledQueue() {
        return new Queue(NOTIFICATION_SCHEDULED_QUEUE, true);
    }

    @Bean
    public Binding notificationScheduledBinding(
            @Qualifier(NOTIFICATION_SCHEDULED_QUEUE) Queue notificationScheduledQueue,
            DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationScheduledQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_SCHEDULED_ROUTING_KEY);
    }

    @Bean(NOTIFICATION_INSTANT_QUEUE)
    public Queue notificationInstantQueue() {
        return new Queue(NOTIFICATION_INSTANT_QUEUE, true);
    }

    @Bean
    public Binding notificationInstantBinding(
            @Qualifier(NOTIFICATION_INSTANT_QUEUE) Queue notificationInstantQueue,
            DirectExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationInstantQueue)
                .to(notificationExchange)
                .with(NOTIFICATION_INSTANT_ROUTING_KEY);
    }
}