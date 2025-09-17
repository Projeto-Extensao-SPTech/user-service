package com.dog_feliz.user_service.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundByEmail extends RuntimeException {
    private final HttpStatus status;

    public UserNotFoundByEmail(String email) {
        super(String.format("User not found by email %s", email));
        this.status = HttpStatus.NOT_FOUND;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
