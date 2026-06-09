package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.shared.exception.ForbiddenUserException;
import com.dog.feliz.user.service.shared.utils.UserTokenValidationUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private UserTokenValidationUtils userTokenValidationUtils;

    @InjectMocks
    private ValidationService validationService;

    @Test
    @DisplayName("Dado token de admin, quando verifyIsAdminUser é chamado, não deve lançar exceção")
    void givenAdmin_whenVerifyIsAdminUser_thenPasses() {
        when(userTokenValidationUtils.isAdminUser()).thenReturn(true);

        assertDoesNotThrow(() -> validationService.verifyIsAdminUser());
    }

    @Test
    @DisplayName("Dado token sem perfil admin, quando verifyIsAdminUser é chamado, deve lançar ForbiddenUserException")
    void givenNonAdmin_whenVerifyIsAdminUser_thenThrowsForbidden() {
        when(userTokenValidationUtils.isAdminUser()).thenReturn(false);

        assertThrows(ForbiddenUserException.class, () -> validationService.verifyIsAdminUser());
    }

    @Test
    @DisplayName("Dado id de usuário correspondente ao token, quando verifyIsValidUserId é chamado, não deve lançar exceção")
    void givenMatchingUserId_whenVerifyIsValidUserId_thenPasses() {
        when(userTokenValidationUtils.isValidUserId(10L)).thenReturn(true);

        assertDoesNotThrow(() -> validationService.verifyIsValidUserId(10L));
    }

    @Test
    @DisplayName("Dado id de usuário diferente do token, quando verifyIsValidUserId é chamado, deve lançar ForbiddenUserException")
    void givenDifferentUserId_whenVerifyIsValidUserId_thenThrowsForbidden() {
        when(userTokenValidationUtils.isValidUserId(99L)).thenReturn(false);

        assertThrows(ForbiddenUserException.class, () -> validationService.verifyIsValidUserId(99L));
    }
}
