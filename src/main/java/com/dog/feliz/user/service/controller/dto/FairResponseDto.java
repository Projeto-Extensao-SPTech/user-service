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

    private Integer interest;

    private List<String> images;

    public FairResponseDto() {}

    public FairResponseDto(FairEntity entity) {
        this.id = entity.getId();
        this.fairDate = entity.getFairDate();
        this.fairHour = entity.getFairHour();
        this.address = new AddressResponseDto(entity.getAddress());
        this.interest = entity.getInterest();
        this.images = entity.getImageKeys();
    }
}
