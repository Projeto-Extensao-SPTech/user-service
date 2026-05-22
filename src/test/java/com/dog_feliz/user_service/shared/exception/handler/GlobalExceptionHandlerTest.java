package com.dog_feliz.user_service.shared.exception.handler;

import com.dog_feliz.user_service.shared.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    @DisplayName("Dada ForbiddenUserException, quando tratada, deve retornar status 403")
    void givenForbidden_whenHandled_thenReturns403() {
        ResponseEntity<Object> response = handler.handleForbiddenUserException(
                new ForbiddenUserException("Invalid user for this operation")
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals(HttpStatus.FORBIDDEN, ((Map<?, ?>) response.getBody()).get("status"));
    }

    @Test
    @DisplayName("Dada UserNotFoundException, quando tratada, deve retornar status 404")
    void givenUserNotFound_whenHandled_thenReturns404() {
        ResponseEntity<Object> response = handler.handleUserNotFoundByMailAddress(
                new UserNotFoundException("User not found")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Dada ConflictUserException, quando tratada, deve retornar status 409")
    void givenConflict_whenHandled_thenReturns409() {
        ResponseEntity<Object> response = handler.handleConflictUser(
                new ConflictUserException("Already exists")
        );

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    @DisplayName("Dada VolunteerNotFoundException, quando tratada, deve retornar status 404")
    void givenVolunteerNotFound_whenHandled_thenReturns404() {
        ResponseEntity<Object> response = handler.handleVolunteerNotFoundException(
                new VolunteerNotFoundException("Volunteer not found")
        );

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("Dada InvalidRefreshTokenException, quando tratada, deve retornar status 401")
    void givenInvalidRefreshToken_whenHandled_thenReturns401() {
        ResponseEntity<Object> response = handler.handleInvalidRefreshTokenException(
                new InvalidRefreshTokenException("Refresh token expired")
        );

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    @DisplayName("Dada IllegalArgumentException, quando tratada, deve retornar status 422")
    void givenIllegalArgument_whenHandled_thenReturns422() {
        ResponseEntity<Object> response = handler.handleIllegalArgumentException(
                new IllegalArgumentException("Invalid input")
        );

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }
}
