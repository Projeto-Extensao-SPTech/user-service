package com.dog_feliz.user_service.shared.exception.handler;

import com.dog_feliz.user_service.shared.exception.AddressNotFoundById;
import com.dog_feliz.user_service.shared.exception.UnauthorizedUser;
import com.dog_feliz.user_service.shared.exception.UserNotFoundByEmail;
import com.dog_feliz.user_service.shared.exception.UserNotFoundById;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundById.class)
    public ResponseEntity<Object> handleUserNotFoundById(UserNotFoundById ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(AddressNotFoundById.class)
    public ResponseEntity<Object> handleAddressNotFoundById(AddressNotFoundById ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(UnauthorizedUser.class)
    public ResponseEntity<Object> handleUnauthorizedUser(UnauthorizedUser ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    @ExceptionHandler(UserNotFoundByEmail.class)
    public ResponseEntity<Object> handleUserNotFoundByEmail(UserNotFoundByEmail ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

}
