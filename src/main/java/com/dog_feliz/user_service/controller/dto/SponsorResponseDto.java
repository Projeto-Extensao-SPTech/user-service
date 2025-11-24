package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.SponsorEntity;

public class SponsorResponseDto {
    private Long id;
    private Long userId;
    private Long addressId;
    private String name;
    private String document;
    private String department;

    public SponsorResponseDto(Long id, Long userId, Long addressId, String name, String document, String department) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.name = name;
        this.document = document;
        this.department = department;
    }

    public SponsorResponseDto(SponsorEntity sponsor) {
        this.id = sponsor.getId();
        this.userId = sponsor.getUser().getId() != null ? sponsor.getUser().getId() : null;
        this.addressId = sponsor.getAddress().getId() != null ? sponsor.getAddress().getId() : null;
        this.name = sponsor.getName();
        this.document = sponsor.getDocument();
        this.department = sponsor.getDepartment();
    }

    // GETTERS E SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
