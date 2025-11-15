package com.dog_feliz.user_service.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @OneToOne
    @JoinColumn(name = "adoption_fair_id")
    private AdoptionFairEntity adoptionFair = null;

    private String description;

    private ZonedDateTime createdAt = ZonedDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public String getDescription() {
        return description;
    }

    public AdoptionFairEntity getAdoptionFair() {
        return adoptionFair;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}

