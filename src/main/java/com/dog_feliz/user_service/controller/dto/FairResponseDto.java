package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.FairEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FairResponseDto {

    private Long id;
    private LocalDate fairDate;
    private LocalDateTime fairHour;
    private AddressResponseDto address;
    private List<String> images;

    public FairResponseDto(){}

    public FairResponseDto(FairEntity entity){
        this.id = entity.getId();
        this.fairDate = entity.getFairDate();
        this.fairHour = entity.getFairHour();
        this.address = new AddressResponseDto(entity.getAddress());
        this.images = entity.getImages();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AddressResponseDto getAddress() {
        return address;
    }

    public void setAddress(AddressResponseDto address) {
        this.address = address;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
