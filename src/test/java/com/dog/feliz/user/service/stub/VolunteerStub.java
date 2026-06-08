package com.dog.feliz.user.service.stub;

import com.dog.feliz.user.service.controller.dto.VolunteerRequestDto;
import com.dog.feliz.user.service.entity.VolunteerEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;

import java.time.LocalDate;

public final class VolunteerStub {

    private VolunteerStub() {
    }

    public static VolunteerRequestDto validRequest(Long userId) {
        VolunteerRequestDto dto = new VolunteerRequestDto();
        dto.setMessage("Disponível para ajudar nos finais de semana");
        dto.setAvailableDate(LocalDate.now().plusDays(7));
        dto.setUserId(userId);
        return dto;
    }

    public static VolunteerEntity entityWithId(Long id, UserEntity user) {
        return new VolunteerEntity(
                id,
                "Quero ajudar",
                LocalDate.now().plusDays(3),
                user
        );
    }
}
