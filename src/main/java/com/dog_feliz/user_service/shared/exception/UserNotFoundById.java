package com.dog_feliz.user_service.shared.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundById extends RuntimeException {
    private final HttpStatus status;

    public UserNotFoundById(Long id) {
        super(String.format("User not found by Id %d", id));
        this.status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
