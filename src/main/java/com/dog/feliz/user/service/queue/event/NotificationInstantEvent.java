package com.dog.feliz.user.service.queue.event;

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