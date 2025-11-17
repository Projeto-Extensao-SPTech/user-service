package com.dog_feliz.user_service.controller.dto;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SponsorshipRequestDto {
    private Long sponsorId;
    private String type;
    private LocalDateTime recurrence;
    private String description;
    private String context;

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(LocalDateTime recurrence) {
        this.recurrence = recurrence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
