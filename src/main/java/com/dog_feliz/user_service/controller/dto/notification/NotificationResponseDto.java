package com.dog_feliz.user_service.controller.dto.notification;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationType;
import java.time.LocalDate;
import java.util.List;

public class NotificationResponseDto {
    private NotificationType type;
    private String message;
    private List<LocalDate> recurrences;

    public NotificationResponseDto() {
    }

    public NotificationResponseDto(NotificationEntity notificationEntity) {
        this.type = notificationEntity.getNotificationType();
        this.message = notificationEntity.getMessage();
        this.recurrences = notificationEntity
                .getNotificationRecurrence()
                .stream()
                .map(recurrence -> recurrence.getRecurrence())
                .toList();
    }

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public List<LocalDate> getRecurrences() {
        return recurrences;
    }
}
