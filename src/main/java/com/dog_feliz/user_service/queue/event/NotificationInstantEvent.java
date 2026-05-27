package com.dog_feliz.user_service.queue.event;

import java.util.UUID;

public record NotificationInstantEvent (
        String eventId,
        String notificationType,
        String recipientMailAddress,
        String message
) {
    public NotificationInstantEvent (
            String notificationType,
            String recipientMailAddress,
            String message
    ) {
        this(UUID.randomUUID().toString(), notificationType, recipientMailAddress, message);
    }
}