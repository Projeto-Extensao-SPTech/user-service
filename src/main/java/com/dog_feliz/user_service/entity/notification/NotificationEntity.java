package com.dog_feliz.user_service.entity.notification;

import com.dog_feliz.user_service.controller.dto.FairResponseDto;
import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.entity.FairEntity;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @JoinColumn(name = "fair_id")
    private FairEntity fair = null;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.REMOVE)
    private List<NotificationRecurrenceEntity> notificationRecurrence = new ArrayList<>();

    private String message;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    private ZonedDateTime createdAt = ZonedDateTime.now();

    public NotificationEntity() {
    }

    public NotificationEntity(NotificationRequestDto notificationRequest) {
        this.notificationType = notificationRequest.getType();
        this.message = notificationRequest.getMessage();
        this.eventDate = notificationRequest.getEventDate();
    }

    public NotificationEntity(NotificationRequestDto notificationRequest, FairEntity fairEntity) {
        this.notificationType = notificationRequest.getType();
        this.message = notificationRequest.getMessage();
        this.eventDate = notificationRequest.getEventDate();
        this.fair = fairEntity;
    }


    public NotificationEntity(NotificationEntity notificationEntity, List<NotificationRecurrenceEntity> recurrences) {
        this.id = notificationEntity.id;
        this.notificationType = notificationEntity.getNotificationType();
        this.message = notificationEntity.getMessage();
        this.eventDate = notificationEntity.getEventDate();
        this.fair = notificationEntity.fair;
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

    public FairEntity getFair() {
        return fair;
    }

    public List<NotificationRecurrenceEntity> getNotificationRecurrence() {
        return notificationRecurrence;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

