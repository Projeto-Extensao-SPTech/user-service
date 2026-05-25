package com.dog_feliz.user_service.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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
}
