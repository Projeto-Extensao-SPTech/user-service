package com.dog_feliz.user_service.controller.dto;

import static org.junit.jupiter.api.Assertions.*;


import com.dog_feliz.user_service.stub.FairStub;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class FairRequestDtoTest {

    @InjectMocks
    FairStub fairStub;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Dado o construtor completo, quando criar um FairRequestDto, deve popular todos os campos corretamente")
    void testFullConstructor() {

       var newFairStub = fairStub.createNewFair();

       var fairDate = LocalDate.of(2025, 12, 2);
       var fairHour = LocalDateTime.of(2025, 12, 2, 12, 30);



        assertEquals(fairDate, newFairStub.getFairDate());
        assertEquals(fairHour, newFairStub.getFairHour());
        assertEquals(1, newFairStub.getImage().size());
        assertEquals("foto.png", newFairStub.getImage().getFirst().getOriginalFilename());
    }

    @Test
    @DisplayName("Dado um FairRequestDto vazio, quando usar setters, deve atualizar corretamente todos os campos")
    void testSetters() {

        FairRequestDto dto = new FairRequestDto();

        LocalDate fairDate = LocalDate.of(2030, 1, 10);
        LocalDateTime fairHour = LocalDateTime.of(2030, 1, 10, 8, 45);

        AddressRequestDto address = new AddressRequestDto(
                "10000000",
                50,
                "Rua Nova",
                "Casa 2",
                "Cidade Y",
                "RJ",
                "Brasil"
        );

        MockMultipartFile file = new MockMultipartFile(
                "image",
                "foto2.png",
                "image/png",
                "balabala".getBytes()
        );

        dto.setFairDate(fairDate);
        dto.setFairHour(fairHour);
        dto.setAddress(address);
        dto.setImage(List.of(file));

        assertEquals(fairDate, dto.getFairDate());
        assertEquals(fairHour, dto.getFairHour());
        assertEquals(address, dto.getAddress());
        assertEquals(1, dto.getImage().size());
        assertEquals("foto2.png", dto.getImage().getFirst().getOriginalFilename());
    }

}
