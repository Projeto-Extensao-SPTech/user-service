package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.FairEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FairResponseDto {
    private Long id;

    private LocalDate fairDate;

    private LocalDateTime fairHour;

    private AddressResponseDto address;

    private List<String> images;

    private long totalInterest;

    private boolean userHasInterest;

    public FairResponseDto() {}

    public FairResponseDto(FairEntity entity, long totalInterest, boolean userHasInterest) {
        this.id = entity.getId();
        this.fairDate = entity.getFairDate();
        this.fairHour = entity.getFairHour();
        this.address = new AddressResponseDto(entity.getAddress());
        this.images = entity.getImageKeys();
        this.totalInterest = totalInterest;
        this.userHasInterest = userHasInterest;
    }
}