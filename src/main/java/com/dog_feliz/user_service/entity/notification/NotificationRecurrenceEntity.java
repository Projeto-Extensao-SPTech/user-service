package com.dog_feliz.user_service.entity.notification;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "notification_recurrence_tb")
public class NotificationRecurrenceEntity {
    @EmbeddedId
    private NotificationRecurrenceId id;

    @ManyToOne
    @MapsId("notificationId") // liga a PK ao campo FK
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

    public NotificationRecurrenceEntity() {}

    public NotificationRecurrenceEntity(NotificationEntity notification, LocalDate recurrence) {
        this.notification = notification;
        this.id = new NotificationRecurrenceId(notification.getId(), recurrence);
    }

    public NotificationRecurrenceId getId() {
        return id;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public LocalDate getRecurrence() {
        return id.getRecurrence();
    }
}
