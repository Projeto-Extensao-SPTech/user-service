package com.dog.feliz.user.service.controller.dto;

public record UpdatePasswordRequestDto(
        String mail,
        String password
) {}

