package com.dog_feliz.user_service.queue.producer;

import com.dog_feliz.user_service.config.RabbitMQConfig;
import com.dog_feliz.user_service.queue.event.NotificationInstantEvent;
import com.dog_feliz.user_service.queue.event.NotificationScheduledEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private final RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotificationScheduled(NotificationScheduledEvent event){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,
                RabbitMQConfig.NOTIFICATION_SCHEDULED_ROUTING_KEY,
                event
        );
    }

    public void sendNotificationInstant(NotificationInstantEvent event){
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.NOTIFICATION_EXCHANGE,
                RabbitMQConfig.NOTIFICATION_INSTANT_ROUTING_KEY,
                event
        );
    }
}