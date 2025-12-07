package com.dog_feliz.user_service.stub;

import com.dog_feliz.user_service.controller.dto.AddressRequestDto;
import com.dog_feliz.user_service.controller.dto.FairRequestDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.FairEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record FairStub() {

    public FairRequestDto createNewFair() {
        MockMultipartFile file = new MockMultipartFile(
                "image",
                "foto.png",
                "image/png",
                "conteudo".getBytes()
        );
        AddressRequestDto adress = new AddressRequestDto(
                "03992231",
                120,
                "Rua do birobiro",
                "Bloco zezinho",
                "SP Garoa",
                "SP",
                "Brasil"
        );

        FairRequestDto request = new FairRequestDto();
        request.setFairDate(LocalDate.of(2025, 12, 2));
        request.setFairHour(LocalDateTime.of(2025, 12, 2, 12, 30));
        request.setAddress(adress);
        request.setImage(List.of(file));
        return request;
    }

    public FairEntity createFairEntity(Long id, AddressEntity address){
        FairEntity fair = new FairEntity();
        fair.setId(id);
        fair.setAddress(address);

        return fair;
    }

    public AddressEntity createAddressEntity(Long id){
        AddressEntity address = new AddressEntity();
        address.setAddress(id);
        return address;
    }
}
