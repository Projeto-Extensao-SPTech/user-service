package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.SponsorshipEntity;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

public class SponsorshipResponseDto {
    private Long id;
    private Long sponsorId;
    private String type;
    private String recurrence;
    private String description;
    private String context;

    public SponsorshipResponseDto(SponsorshipEntity entity) {
        this.id = entity.getId();
        this.sponsorId = entity.getSponsor().getId();
        this.type = entity.getType();
        this.recurrence = entity.getRecurrence() != null ? entity.getRecurrence().toString() : null;
        this.description = entity.getDescription();
        this.context = entity.getContext();
    }
}
