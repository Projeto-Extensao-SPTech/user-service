package com.dog_feliz.user_service.stub;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FairStub {

    public FairRequestDto createNewFair() {
        MockMultipartFile image = new MockMultipartFile(
                "image",
                "foto.png",
                "image/png",
                "conteudo-fake".getBytes()
        );

        FairRequestDto dto = new FairRequestDto();
        dto.setFairDate(LocalDate.now().plusDays(7));
        dto.setFairHour(LocalDateTime.now().plusDays(7).truncatedTo(ChronoUnit.MINUTES));
        dto.setAddress(createAddressRequestDto());
        dto.setImages(List.of(image));
        return dto;
    }

    public AddressRequestDto createAddressRequestDto() {
        AddressRequestDto dto = new AddressRequestDto();
        dto.setStreet("Rua das Flores");
        dto.setNumber(123);
        dto.setCity("São Paulo");
        dto.setState("SP");
        dto.setZip_code("01001-000");
        return dto;
    }

    public AddressEntity createAddressEntity(Long id) {
        AddressEntity address = new AddressEntity();
        address.setId(id);
        address.setStreet("Rua das Flores");
        address.setNumber(123);
        address.setCity("São Paulo");
        address.setState("SP");
        address.setZipCode("01001-000");
        return address;
    }

    public FairEntity createFairEntity(Long id, AddressEntity address) {
        FairEntity fair = new FairEntity();
        fair.setId(id);
        fair.setFairDate(LocalDate.now().plusDays(7));
        fair.setFairHour(LocalDateTime.now().plusDays(7));
        fair.setAddress(address);
        fair.setInterest(0);
        fair.setImageKeys(List.of("fair/uuid-stub_foto.png"));
        return fair;
    }

    public FairEntity createFairEntityWithMultipleImages(Long id, AddressEntity address) {
        FairEntity fair = createFairEntity(id, address);
        fair.setImageKeys(List.of(
                "fair/uuid-1_foto1.png",
                "fair/uuid-2_foto2.png"
        ));
        return fair;
    }
}