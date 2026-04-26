package com.dog_feliz.user_service.controller.dto;

import java.time.LocalDate;

public record DashboardVolunteerKpiDto(
        String day,
        Long total
) {}
