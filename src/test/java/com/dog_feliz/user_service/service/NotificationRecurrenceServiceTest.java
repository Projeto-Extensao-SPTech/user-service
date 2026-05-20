package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationType;
import com.dog_feliz.user_service.repository.NotificationRecurrenceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationRecurrenceServiceTest {

    @Mock
    private NotificationRecurrenceRepository notificationRecurrenceRepository;

    @InjectMocks
    private NotificationRecurrenceService notificationRecurrenceService;

    @Test
    @DisplayName("Dada data do evento no passado, quando validateNotificationRecurrence é chamado, deve lançar IllegalArgumentException")
    void givenPastEventDate_whenValidate_thenThrows() {
        LocalDate past = LocalDate.now().minusDays(1);

        assertThrows(IllegalArgumentException.class,
                () -> notificationRecurrenceService.validateNotificationRecurrence(past, List.of(1)));
    }

    @Test
    @DisplayName("Dada recorrência anterior a hoje, quando validateNotificationRecurrence é chamado, deve lançar IllegalArgumentException")
    void givenRecurrenceBeforeToday_whenValidate_thenThrows() {
        LocalDate eventDate = LocalDate.now().plusDays(2);

        assertThrows(IllegalArgumentException.class,
                () -> notificationRecurrenceService.validateNotificationRecurrence(eventDate, List.of(5)));
    }

    @Test
    @DisplayName("Dadas datas futuras válidas, quando validateNotificationRecurrence é chamado, a validação deve passar")
    void givenValidDates_whenValidate_thenPasses() {
        LocalDate eventDate = LocalDate.now().plusDays(10);

        assertDoesNotThrow(() ->
                notificationRecurrenceService.validateNotificationRecurrence(eventDate, List.of(1, 2)));
    }

    @Test
    @DisplayName("Dada notificação e recorrências, quando register é chamado, deve persistir as entidades de recorrência")
    void givenNotification_whenRegister_thenPersistsRecurrences() {
        NotificationEntity notification = new NotificationEntity();
        notification.setId(1L);
        ReflectionTestUtils.setField(notification, "notificationType", NotificationType.FAIR);
        ReflectionTestUtils.setField(notification, "message", "Feira de adoção");

        when(notificationRecurrenceRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = notificationRecurrenceService.register(
                notification,
                LocalDate.now().plusDays(15),
                List.of(1, 3)
        );

        assertFalse(result.isEmpty());
        assertTrue(result.size() >= 2);
    }
}
