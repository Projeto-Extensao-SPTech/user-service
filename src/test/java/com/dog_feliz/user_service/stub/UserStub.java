package com.dog_feliz.user_service.stub;

import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.entity.user.UserType;
import com.dog_feliz.user_service.support.TestConstants;
import org.springframework.test.util.ReflectionTestUtils;

public final class UserStub {

    private UserStub() {
    }

    public static UserRequestDto validRequest() {
        return validRequest(false);
    }

    public static UserRequestDto validRequest(boolean isAdmin) {
        return new UserRequestDto(
                UserType.PF,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                TestConstants.VALID_PHONE,
                AddressStub.validRequest(),
                TestConstants.VALID_EMAIL,
                TestConstants.VALID_PASSWORD,
                isAdmin
        );
    }

    public static UserEntity entityWithId(Long id, AddressEntity address) {
        UserEntity user = new UserEntity(
                validRequest(),
                address,
                "$2a$10$encodedPasswordHashPlaceholder",
                "mail-hash-placeholder"
        );
        ReflectionTestUtils.setField(user, "id", id);
        return user;
    }

    public static UserEntity entityWithId(Long id) {
        return entityWithId(id, AddressStub.entityWithId(1L));
    }
}
