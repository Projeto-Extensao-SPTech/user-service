package com.dog_feliz.user_service.notification.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private String description;

    private ZonedDateTime createdAt;
}

