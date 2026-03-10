package com.dog_feliz.user_service.queue.producer;

import com.dog_feliz.user_service.queue.event.NotificationCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationProducer {
    private RabbitTemplate rabbitTemplate;

    public NotificationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNotification(NotificationCreatedEvent event){

        rabbitTemplate.convertAndSend(
                "notification.exchange",
                "notification.create",
                event
        );
    }
}
