package com.dog_feliz.user_service.entity.notification;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.entity.AdoptionFairEntity;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "notification_tb")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToOne
    @JoinColumn(name = "adoption_fair_id")
    private AdoptionFairEntity adoptionFair = null;

    @OneToMany
    private List<NotificationRecurrenceEntity> notificationRecurrence;

    private String message;

    private ZonedDateTime createdAt = ZonedDateTime.now();

    public NotificationEntity() {
    }

    public NotificationEntity(NotificationRequestDto notificationRequest) {
        this.notificationType = notificationRequest.getType();
        this.message = notificationRequest.getMessage();
    }

    public NotificationEntity(NotificationRequestDto notificationRequest, AdoptionFairEntity adoptionFair) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getMessage() {
        return message;
    }

    public AdoptionFairEntity getAdoptionFair() {
        return adoptionFair;
    }

    public List<NotificationRecurrenceEntity> getNotificationRecurrence() {
        return notificationRecurrence;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

