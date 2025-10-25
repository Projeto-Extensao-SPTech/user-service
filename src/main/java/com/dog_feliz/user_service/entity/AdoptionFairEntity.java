package com.dog_feliz.user_service.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
public class AdoptionFairEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ZonedDateTime date;
    private ZonedDateTime createdAt;

    @Entity
    @Table(name = "notification")
    public static class NotificationEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Enumerated(EnumType.STRING)
        private NotificationType notificationType;

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

        public void setNotificationType(NotificationType notificationType) {
            this.notificationType = notificationType;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ZonedDateTime getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(ZonedDateTime createdAt) {
            this.createdAt = createdAt;
        }
    }

    public enum NotificationType {
        ADOPTION_FAIR,
        DONATION,
        GENERAL,
        VOLUNTEER
    }
}
