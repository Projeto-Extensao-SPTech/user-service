package com.dog_feliz.user_service.controller.dto.notification;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.entity.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.List;

public class NotificationRequestDto {
    private NotificationType type;
    @JsonProperty("event_date_time")
    private ZonedDateTime eventDateTime;
    private String message;
    private AddressRequestDto address;
    private List<AvailableRecurrence> recurrences;

    public NotificationRequestDto() {
    }

    public NotificationType getType() {
        return type;
    }

    public ZonedDateTime getEventDateTime() {
        return eventDateTime;
    }

    public String getMessage() {
        return message;
    }

    public AddressRequestDto getAddress() {
        return address;
    }

    public List<AvailableRecurrence> getRecurrences() {
        return recurrences;
    }
}

