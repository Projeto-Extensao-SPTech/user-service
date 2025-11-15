package com.dog_feliz.user_service.controller.dto.notification;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

public class NotificationResponseDto {
    private String type;
    @JsonProperty("event_date_time")
    private ZonedDateTime eventDateTime;
    private String message;
    private AddressRequestDto address;
    private List<LocalDate> recurrences;

    public NotificationResponseDto() {
    }

    public String getType() {
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

    public List<LocalDate> getRecurrences() {
        return recurrences;
    }
}
