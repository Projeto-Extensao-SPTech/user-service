package com.dog_feliz.user_service.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedUser extends RuntimeException {
    private final HttpStatus status;

    public UnauthorizedUser(String email, String password) {
        super(String.format("Unauthorized user by email %s and password %s", email, password));
        status = HttpStatus.UNAUTHORIZED;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
