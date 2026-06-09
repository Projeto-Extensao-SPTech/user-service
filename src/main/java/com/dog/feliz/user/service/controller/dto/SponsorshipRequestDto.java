package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.SponsorshipEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SponsorshipRequestDto {
    @JsonProperty("sponsor_id")
    private Long sponsorId;

    private String type;

    private String description;

    private String department;

    public SponsorshipRequestDto() {}

    public SponsorshipRequestDto(SponsorshipEntity sponsorshipEntity) {
        if (sponsorshipEntity == null) {
            return;
        }
        this.sponsorId = sponsorshipEntity.getSponsor() != null ? sponsorshipEntity.getSponsor().getId() : null;
        this.type = sponsorshipEntity.getType();
        this.description = sponsorshipEntity.getDescription();
        this.department = sponsorshipEntity.getDepartment();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
