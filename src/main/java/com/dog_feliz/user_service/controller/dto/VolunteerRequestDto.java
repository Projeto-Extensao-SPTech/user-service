package com.dog_feliz.user_service.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class VolunteerRequestDto {

    private String message;

    private LocalDate availableDate;

    @JsonProperty("user_id")
    private Long userId;

    public String getMessage() {
        return message;
    }

    public LocalDate getAvailableDate() {
        return availableDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setAvailableDate(LocalDate availableDate) {
        this.availableDate = availableDate;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
