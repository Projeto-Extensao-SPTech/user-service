package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.user.UserType;
import com.dog_feliz.user_service.stub.AddressStub;
import com.dog_feliz.user_service.support.TestConstants;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserRequestDtoValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("Dada requisição de usuário válida, quando validada, não deve reportar violações")
    void givenValidRequest_whenValidated_thenNoViolations() {
        UserRequestDto dto = new UserRequestDto(
                UserType.PF,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                TestConstants.VALID_PHONE,
                AddressStub.validRequest(),
                TestConstants.VALID_EMAIL,
                TestConstants.VALID_PASSWORD,
                false
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Dado e-mail inválido, quando validado, deve reportar violação de constraint de e-mail")
    void givenInvalidEmail_whenValidated_thenReportsEmailViolation() {
        UserRequestDto dto = new UserRequestDto(
                UserType.PF,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                TestConstants.VALID_PHONE,
                AddressStub.validRequest(),
                "not-an-email",
                TestConstants.VALID_PASSWORD,
                false
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().contains("mailAddress")
                || v.getMessage().toLowerCase().contains("email")));
    }

    @Test
    @DisplayName("Dada senha curta, quando validada, deve reportar violação de tamanho")
    void givenShortPassword_whenValidated_thenReportsSizeViolation() {
        UserRequestDto dto = new UserRequestDto(
                UserType.PF,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                TestConstants.VALID_PHONE,
                AddressStub.validRequest(),
                TestConstants.VALID_EMAIL,
                "short",
                false
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Dado formato de telefone inválido, quando validado, deve reportar violação de padrão")
    void givenInvalidPhone_whenValidated_thenReportsPatternViolation() {
        UserRequestDto dto = new UserRequestDto(
                UserType.PF,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                "123",
                AddressStub.validRequest(),
                TestConstants.VALID_EMAIL,
                TestConstants.VALID_PASSWORD,
                false
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Dado tipo de usuário nulo, quando validado, deve reportar violação de not-null")
    void givenNullType_whenValidated_thenReportsNotNullViolation() {
        UserRequestDto dto = new UserRequestDto(
                null,
                TestConstants.VALID_NAME,
                TestConstants.VALID_DOCUMENT,
                TestConstants.VALID_PHONE,
                AddressStub.validRequest(),
                TestConstants.VALID_EMAIL,
                TestConstants.VALID_PASSWORD,
                false
        );

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }
}
