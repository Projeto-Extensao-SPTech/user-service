package com.dog.feliz.user.service.controller.dto;

public class SponsorInfoDto {
    private Long id;

    private String name;

    private String email;

    private String phone;

    public SponsorInfoDto(Long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public SponsorInfoDto() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
