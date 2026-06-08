package com.dog_feliz.user_service.controller.dto;

public record UpdatePasswordRequestDto(
        String mail,
        String password
) {}

