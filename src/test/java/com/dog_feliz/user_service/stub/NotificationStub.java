package com.dog_feliz.user_service.stub;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.entity.notification.NotificationType;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

public class NotificationStub {
    public NotificationRequestDto createValidNotificationRequest() {
        NotificationRequestDto dto = new NotificationRequestDto();

        set(dto, "type", NotificationType.DONATION);
        set(dto, "fairId", 10L);
        set(dto, "message", "Feira de adoção neste fim de semana!");
        set(dto, "eventDate", LocalDate.now().plusDays(3));
        set(dto, "recurrences", List.of(0, 7, 14));

        return dto;
    }

    public NotificationRequestDto createNotificationWithCustomValues(
            NotificationType type,
            Long fairId,
            String message,
            LocalDate eventDate,
            List<Integer> recurrences
    ) {
        NotificationRequestDto dto = new NotificationRequestDto();

        set(dto, "type", type);
        set(dto, "fairId", fairId);
        set(dto, "message", message);
        set(dto, "eventDate", eventDate);
        set(dto, "recurrences", recurrences);

        return dto;
    }

    private void set(Object target, String fieldName, Object value) {
        try {
            Field field = NotificationRequestDto.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao setar campo via reflection: " + fieldName, e);
        }
    }
}
