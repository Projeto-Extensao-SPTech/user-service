package com.dog_feliz.user_service.queue.event;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record NotificationScheduledEvent(
        String eventId,
        String notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
) {
    public NotificationScheduledEvent(
            String notificationType,
            Long fairId,
            String message,
            LocalDate eventDate,
            List<Integer> recurrence
    ) {
        this(
                UUID.randomUUID().toString(),
                notificationType,
                fairId,
                message,
                eventDate,
                recurrence
        );
    }
}