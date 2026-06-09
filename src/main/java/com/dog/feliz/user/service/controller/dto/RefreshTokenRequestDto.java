package com.dog.feliz.user.service.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDto(
    @NotBlank
    String jwtToken,
    @NotBlank
    String refreshToken,
    @NotBlank
    String password
){}
