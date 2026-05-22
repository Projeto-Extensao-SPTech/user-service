package com.dog_feliz.user_service.stub;

import com.dog_feliz.user_service.controller.dto.DonationRequestDto;
import org.springframework.mock.web.MockMultipartFile;

public final class DonationStub {

    private DonationStub() {
    }

    public static DonationRequestDto validRequest() {
        DonationRequestDto dto = new DonationRequestDto();
        dto.setName("Ração Premium");
        dto.setType("Alimento");
        dto.setAmount(5);
        dto.setState("Novo");
        dto.setDescription("Pacote de 10kg");
        dto.setShippingMethod("Entrega");
        dto.setCollectionCenterId(1);
        return dto;
    }

    public static DonationRequestDto validRequestWithImage() {
        DonationRequestDto dto = validRequest();
        dto.setImage(new MockMultipartFile(
                "image",
                "doacao.png",
                "image/png",
                "conteudo-imagem".getBytes()
        ));
        return dto;
    }
}
