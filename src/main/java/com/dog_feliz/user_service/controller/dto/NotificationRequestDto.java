package com.dog_feliz.user_service.controller.dto;

import com.dog_feliz.user_service.entity.notification.NotificationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public class NotificationRequestDto {
    @NotBlank(message = "O tipo da notificação é obrigatório.")
    private NotificationType type;

    @JsonProperty("adoption_fair_id")
    @NotNull(message = "O ID da feira de adoção é obrigatório.")
    @Positive(message = "O ID da feira deve ser um número positivo.")
    private Long adoptionFairId;

    @Size(max = 255, message = "A mensagem deve ter no máximo 255 caracteres.")
    private String message;

    @JsonProperty("event_date")
    @NotNull(message = "A data do evento é obrigatória.")
    @Future(message = "A data do evento deve ser hoje ou uma data futura.")
    private LocalDate eventDate;

    @NotNull(message = "A lista de recorrências não pode ser nula.")
    @Size(min = 1, message = "Pelo menos uma recorrência deve ser informada.")
    private List<
            @NotNull(message = "A recorrência não pode conter valores nulos.")
            @Positive(message = "Cada recorrência deve ser maior ou igual a 0.")
                    Integer
            > recurrences;

    public NotificationRequestDto() {
    }

    public NotificationType getType() {
        return type;
    }

    public Long getAdoptionFairId() {
        return adoptionFairId;
    }

    public String getMessage() {
        return message;
    }

    public List<Integer> getRecurrences() {
        return recurrences;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }
}

