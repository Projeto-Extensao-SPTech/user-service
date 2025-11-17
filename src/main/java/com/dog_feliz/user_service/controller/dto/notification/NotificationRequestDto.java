package com.dog_feliz.user_service.controller.dto.notification;

import com.dog_feliz.user_service.entity.notification.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

public class NotificationRequestDto {
    private NotificationType type;
    @JsonProperty("adoption_fair_id")
    private Long adoptionFairId;
    private String message;
    @JsonProperty("event_date")
    private LocalDate eventDate;
    private List<Integer> recurrences;

    public NotificationRequestDto() {
    }

    public NotificationType getType() {
        return type;
    }

    public Long getAdoptionFairId() {
        return adoptionFairId;
    }

    public String getMessage() {
        return message;
    }

    public List<Integer> getRecurrences() {
        return recurrences;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }
}

