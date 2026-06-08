package com.dog.feliz.user.service.stub;

import com.dog.feliz.user.service.controller.dto.AddressRequestDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.support.TestConstants;
import org.springframework.test.util.ReflectionTestUtils;

public final class AddressStub {

    private AddressStub() {
    }

    public static AddressRequestDto validRequest() {
        return new AddressRequestDto(
                TestConstants.VALID_ZIP_CODE,
                100,
                "Avenida Paulista",
                "Apto 10",
                "São Paulo",
                "SP",
                "Brasil"
        );
    }

    public static AddressEntity entityWithId(Long id) {
        AddressEntity address = new AddressEntity(validRequest());
        ReflectionTestUtils.setField(address, "id", id);
        return address;
    }
}
