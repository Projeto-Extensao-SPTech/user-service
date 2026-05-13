package com.dog_feliz.user_service.queue.event;

public record NotificationInstantEvent (
        String notificationType,
        String recipientMailAddress,
        String message
) {}
