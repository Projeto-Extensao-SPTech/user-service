package com.dog_feliz.user_service.controller.dto;

public record DashboardMonthlyRegistrationDto(
        String month,
        Long total
) {}
