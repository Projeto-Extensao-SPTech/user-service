package com.dog_feliz.user_service.queue.event;


import java.time.LocalDate;
import java.util.List;

public record NotificationScheduledEvent(
        String notificationType,
        Long fairId,
        String message,
        LocalDate eventDate,
        List<Integer> recurrence
) {
}
