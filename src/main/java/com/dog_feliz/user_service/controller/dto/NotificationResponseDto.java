package com.dog_feliz.user_service.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationResponseDto {
    private NotificationType type;
    private String message;
    private List<LocalDate> recurrences;
    @JsonProperty("created_at")
    private ZonedDateTime createdAt;

    public NotificationResponseDto() {}

    public NotificationType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public List<LocalDate> getRecurrences() {
        return recurrences;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
