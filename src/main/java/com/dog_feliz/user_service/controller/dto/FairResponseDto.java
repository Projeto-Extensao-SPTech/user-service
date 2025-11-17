package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.FairImageEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FairResponseDto {

    private Long id;
    private LocalDate fairDate;
    private LocalDateTime fairHour;
    private AddressRequestDto address;
    private List<String> images;

    public FairResponseDto(){}

    public FairResponseDto(FairEntity entity){
        this.id = entity.getId();
        this.fairDate = entity.getFairDate();
        this.fairHour = entity.getFairHour();
        this.address = entity.getAddress();
        this.images = entity.getImage()
                .stream()
                .map(FairImageEntity::getImagePath)
                .toList();

    }
}
