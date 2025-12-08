package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.entity.notification.NotificationRecurrenceEntity;
import com.dog_feliz.user_service.entity.notification.NotificationType;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.repository.NotificationRepository;
import com.dog_feliz.user_service.service.mail.strategy.GmailSenderStrategy;
import com.dog_feliz.user_service.stub.NotificationStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private NotificationRecurrenceService notificationRecurrenceService;

    @Mock
    private GmailSenderStrategy gmailSenderStrategy;

    @Mock
    private FairRepository fairRepository;

    @InjectMocks
    private NotificationService notificationService;

    private final NotificationStub stub = new NotificationStub();

    @Test
    @DisplayName("Dada uma chamada para criar uma notificação, quando feira existe e recorrências são válidas deve salvar e retornar notificação com recorrências")
    void registerNotificationWithFair() {
        NotificationRequestDto request = stub.createValidNotificationRequest();

        FairEntity fair = new FairEntity();
        fair.setId(request.getFairId());

        NotificationEntity saved = new NotificationEntity(request, fair);
        saved.setId(1L);

        List<NotificationRecurrenceEntity> recurrences =
                List.of(new NotificationRecurrenceEntity(), new NotificationRecurrenceEntity());

        when(fairRepository.findById(request.getFairId()))
                .thenReturn(Optional.of(fair));

        when(notificationRepository.save(any(NotificationEntity.class)))
                .thenReturn(saved);

        when(notificationRecurrenceService.register(saved, request.getEventDate(), request.getRecurrences()))
                .thenReturn(recurrences);

        NotificationEntity result = notificationService.register(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(NotificationType.DONATION, result.getNotificationType());
        assertEquals(2, result.getNotificationRecurrence().size());

        verify(notificationRecurrenceService).validateNotificationRecurrence(request.getEventDate(), request.getRecurrences());
    }

    @Test
    @DisplayName("Dada uma chamada para criar uma notificação sem feira, deve salvar corretamente uma notificação sem feira")
    void registerNotificationWithoutFair() {
        NotificationRequestDto request = stub.createNotificationWithCustomValues(
                NotificationType.DONATION,
                null,
                "Mensagem teste",
                LocalDate.now().plusDays(5),
                List.of(1, 3)
        );

        NotificationEntity saved = new NotificationEntity(request);
        saved.setId(50L);

        when(notificationRepository.save(any(NotificationEntity.class)))
                .thenReturn(saved);

        when(notificationRecurrenceService.register(saved, request.getEventDate(), request.getRecurrences()))
                .thenReturn(List.of(new NotificationRecurrenceEntity()));

        NotificationEntity result = notificationService.register(request);

        assertNotNull(result);
        assertEquals(50L, result.getId());
        assertNull(result.getFair());
        assertEquals("Mensagem teste", result.getMessage());
    }

    @Test
    @DisplayName("Dada uma chamada para obter uma notificação pelo id, quando id não existe deve retornar NOT_FOUND")
    void getNotificationByIdNotFoundError() {

        when(notificationRepository.findById(999L))
                .thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> notificationService.getById(999L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    @DisplayName("Dada uma chamada para obter uma notificação pelo id, quando id existe deve retornar notificação")
    void getNotificationById() {
        NotificationRequestDto request = stub.createValidNotificationRequest();
        NotificationEntity entity = new NotificationEntity(request);
        entity.setId(1L);

        when(notificationRepository.findById(entity.getId()))
                .thenReturn(Optional.of(entity));

        NotificationEntity result = notificationService.getById(entity.getId());

        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
    }

    @Test
    @DisplayName("Dado envio diário de notificações, deve buscar as do dia e chamar envio em massa")
    void sendTodayNotifications() {
        NotificationRequestDto request = stub.createValidNotificationRequest();

        NotificationEntity entity = new NotificationEntity(request);
        entity.setId(1L);

        when(notificationRepository.findByRecurrenceDate(LocalDate.now()))
                .thenReturn(List.of(entity));

        doNothing().when(gmailSenderStrategy).sendBulkMail(anyList());

        assertDoesNotThrow(() -> notificationService.sendTodayNotifications());

        verify(gmailSenderStrategy, times(1)).sendBulkMail(anyList());
    }

    @Test
    @DisplayName("Dada uma chamada para registrar notificação, quando fairId não encontrado deve retornar NOT_FOUND")
    void registerNotificationFairNotFound() {
        NotificationRequestDto request = stub.createValidNotificationRequest();

        when(fairRepository.findById(request.getFairId()))
                .thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> notificationService.register(request)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(fairRepository, times(1)).findById(request.getFairId());
    }

    @Test
    @DisplayName("Dado uma chamada para deletar uma notificação, quando id existe deve deletar a mesma")
    void deleteNotification() {
        NotificationRequestDto request = stub.createValidNotificationRequest();

        NotificationEntity entity = new NotificationEntity(request);
        entity.setId(1L);

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(entity));

        assertDoesNotThrow(() -> notificationService.deleteById(1L));
        verify(notificationRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Dado uma chamada para deletar uma notificação, quando id existe deve retornar NOT_FOUND")
    void deleteNotificationNotFound() {
        when(notificationRepository.findById(1L))
                .thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> notificationService.deleteById(1L)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}
