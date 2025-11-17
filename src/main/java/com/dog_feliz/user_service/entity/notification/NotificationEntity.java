package com.dog_feliz.user_service.entity.notification;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.entity.AdoptionFairEntity;
import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE)
    private List<NotificationRecurrenceEntity> notificationRecurrence = new ArrayList<>();

    private String message;

    private ZonedDateTime createdAt = ZonedDateTime.now();

    public NotificationEntity() {
    }

    public NotificationEntity(NotificationRequestDto notificationRequest) {
        this.notificationType = notificationRequest.getType();
        this.message = notificationRequest.getMessage();
    }

    public NotificationEntity(NotificationRequestDto notificationRequest, AdoptionFairEntity adoptionFair) {
        this.notificationType = notificationRequest.getType();
        this.message = notificationRequest.getMessage();
        this.adoptionFair = adoptionFair;
    }


    public NotificationEntity(NotificationEntity notificationEntity, List<NotificationRecurrenceEntity> recurrences) {
        this.id = notificationEntity.id;
        this.notificationType = notificationEntity.getNotificationType();
        this.message = notificationEntity.getMessage();
        this.adoptionFair = notificationEntity.adoptionFair;
        this.createdAt = notificationEntity.createdAt;
        this.notificationRecurrence = recurrences;
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

