package com.dog_feliz.user_service.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.*;

@Entity
@Table(name = "notification_recurrence")
public class NotificationRecurrenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private NotificationEntity notification;

    @ManyToOne
    @JoinColumn(name = "recurrence_id")
    private RecurrenceEntity recurrence;

    public NotificationRecurrenceEntity() {
    }

    public Long getId() {
        return id;
    }

    public NotificationEntity getNotification() {
        return notification;
    }

    public RecurrenceEntity getRecurrence() {
        return recurrence;
    }
}
