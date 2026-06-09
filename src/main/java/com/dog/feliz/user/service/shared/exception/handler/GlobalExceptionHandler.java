package com.dog.feliz.user.service.shared.exception.handler;

import com.dog.feliz.user.service.shared.exception.AddressNotFoundException;
import com.dog.feliz.user.service.shared.exception.ConflictUserException;
import com.dog.feliz.user.service.shared.exception.FairInterestConflictException;
import com.dog.feliz.user.service.shared.exception.ForbiddenUserException;
import com.dog.feliz.user.service.shared.exception.InvalidRecoveryCodeException;
import com.dog.feliz.user.service.shared.exception.InvalidRefreshTokenException;
import com.dog.feliz.user.service.shared.exception.MailSenderException;
import com.dog.feliz.user.service.shared.exception.ShipmentNotFoundException;
import com.dog.feliz.user.service.shared.exception.SponsorshipNotFoundException;
import com.dog.feliz.user.service.shared.exception.UnauthorizedUserException;
import com.dog.feliz.user.service.shared.exception.UserNotFoundException;
import com.dog.feliz.user.service.shared.exception.VolunteerNotFoundException;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(AddressNotFoundException.class)
    public ResponseEntity<Object> handleAddressNotFoundById(AddressNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<Object> handleUnauthorizedUser(UnauthorizedUserException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundByMailAddress(UserNotFoundException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(ConflictUserException.class)
    public ResponseEntity<Object> handleConflictUser(ConflictUserException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(MailSenderException.class)
    public ResponseEntity<Object> handleMailSenderException(MailSenderException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getDetailMessageArguments());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleValidationException(RuntimeException ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Object> handleHttpServerErrorException(HttpServerErrorException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<Object> handleInvalidRefreshTokenException(InvalidRefreshTokenException ex) {
        HttpStatus status = ex.getStatus();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(ShipmentNotFoundException.class)
    public ResponseEntity<Object> handleShipmentNotFoundException(ShipmentNotFoundException ex) {
        HttpStatus status = ex.getStatus();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(ForbiddenUserException.class)
    public ResponseEntity<Object> handleForbiddenUserException(ForbiddenUserException ex) {
        HttpStatus status = ex.getStatus();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(VolunteerNotFoundException.class)
    public ResponseEntity<Object> handleVolunteerNotFoundException(VolunteerNotFoundException ex) {
        HttpStatus status = ex.getStatus();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(SponsorshipNotFoundException.class)
    public ResponseEntity<Object> handleSponsorshipNotFoundException(SponsorshipNotFoundException ex) {
        HttpStatus status = ex.getStatus();
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status);
        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Credenciais inválidas");
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<Object> handleInternalAuthenticationServiceException(
            InternalAuthenticationServiceException ex
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Credenciais inválidas");
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(FairInterestConflictException.class)
    public ResponseEntity<Map<String, Object>> handleFairInterestConflict(
            FairInterestConflictException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus().value());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(InvalidRecoveryCodeException.class)
    public ResponseEntity<Object> handleInvalidRecoveryCode(InvalidRecoveryCodeException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.UNPROCESSABLE_ENTITY);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(body);
    }
}
