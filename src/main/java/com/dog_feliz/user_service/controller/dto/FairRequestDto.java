package com.dog_feliz.user_service.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FairRequestDto {
    private LocalDate fairDate;
    private LocalDateTime fairHour;
    private AddressRequestDto address;
    private List<MultipartFile> images;

    public FairRequestDto() {
    }

    public FairRequestDto(List<MultipartFile> image, AddressRequestDto address, LocalDateTime fairHour, LocalDate fairDate) {
        this.images = image;
        this.address = address;
        this.fairHour = fairHour;
        this.fairDate = fairDate;
    }

    public LocalDate getFairDate() {
        return fairDate;
    }

    public void setFairDate(LocalDate fairDate) {
        this.fairDate = fairDate;
    }

    public LocalDateTime getFairHour() {
        return fairHour;
    }

    public void setFairHour(LocalDateTime fairHour) {
        this.fairHour = fairHour;
    }

    public AddressRequestDto getAddress() {
        return address;
    }

    public void setAddress(AddressRequestDto address) {
        this.address = address;
    }

    public List<MultipartFile> getImage() {
        return images;
    }

    public void setImage(List<MultipartFile> image) {
        this.images = image;
    }
}
