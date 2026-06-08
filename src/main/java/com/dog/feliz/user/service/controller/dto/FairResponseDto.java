package com.dog.feliz.user.service.controller.dto;

import com.dog.feliz.user.service.entity.FairEntity;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FairResponseDto {
    private final Long id;
    private final LocalDate fairDate;
    private final LocalDateTime fairHour;
    private final AddressResponseDto address;
    private final List<String> images;
    private final long totalInterest;
    private final boolean userHasInterest;

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