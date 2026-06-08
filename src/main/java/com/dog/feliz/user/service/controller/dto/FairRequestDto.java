package com.dog.feliz.user.service.controller.dto;

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
}
