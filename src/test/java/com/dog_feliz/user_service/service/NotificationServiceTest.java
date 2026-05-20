package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.FairEntity;
import com.dog_feliz.user_service.entity.notification.NotificationType;
import com.dog_feliz.user_service.queue.event.NotificationCreatedEvent;
import com.dog_feliz.user_service.queue.producer.NotificationProducer;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.service.mail.MailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
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
    private FairRepository fairRepository;

    @Mock
    private NotificationProducer notificationProducer;

    @Mock
    private MailService mailService;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private NotificationService notificationService;

    @Test
    @DisplayName("Dado um request com fairId válido, deve validar a feira e publicar o evento na fila")
    void registerWithValidFairIdShouldPublishEvent() {
        NotificationRequestDto request = buildRequest(NotificationType.DONATION, 10L, "Mensagem", LocalDate.now().plusDays(3), List.of(1, 2));

        FairEntity fair = new FairEntity();
        fair.setId(10L);

        when(fairRepository.findById(10L)).thenReturn(Optional.of(fair));

        notificationService.register(request);

        ArgumentCaptor<NotificationCreatedEvent> captor = ArgumentCaptor.forClass(NotificationCreatedEvent.class);
        verify(notificationProducer, times(1)).sendNotification(captor.capture());

        NotificationCreatedEvent event = captor.getValue();
        assertEquals("DONATION", event.notificationType());
        assertEquals(10L, event.fairId());
        assertEquals("Mensagem", event.message());
        assertEquals(List.of(1, 2), event.recurrence());
    }

    @Test
    @DisplayName("Dado um request sem fairId, deve publicar o evento sem validar feira")
    void registerWithoutFairIdShouldPublishEventWithoutFairValidation() {
        NotificationRequestDto request = buildRequest(NotificationType.DONATION, null, "Mensagem sem feira", LocalDate.now().plusDays(1), List.of(3));

        notificationService.register(request);

        verify(fairRepository, never()).findById(any());
        verify(notificationProducer, times(1)).sendNotification(any(NotificationCreatedEvent.class));
    }

    @Test
    @DisplayName("Dado um request com fairId inexistente, deve lançar NOT_FOUND e não publicar evento")
    void registerWithInvalidFairIdShouldThrowNotFound() {
        NotificationRequestDto request = buildRequest(NotificationType.VOLUNTEER, 99L, "Mensagem", LocalDate.now().plusDays(1), List.of(1));

        when(fairRepository.findById(99L)).thenReturn(Optional.empty());

        HttpClientErrorException exception = assertThrows(
                HttpClientErrorException.class,
                () -> notificationService.register(request)
        );

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(notificationProducer, never()).sendNotification(any());
    }

    @Test
    @DisplayName("Dado um request válido, o evento publicado deve conter os dados corretos do request")
    void registerShouldMapRequestFieldsToEventCorrectly() {
        LocalDate eventDate = LocalDate.of(2025, 6, 15);
        NotificationRequestDto request = buildRequest(NotificationType.DONATION, null, "Doação amanhã", eventDate, List.of(1, 7));

        notificationService.register(request);

        ArgumentCaptor<NotificationCreatedEvent> captor = ArgumentCaptor.forClass(NotificationCreatedEvent.class);
        verify(notificationProducer).sendNotification(captor.capture());

        NotificationCreatedEvent event = captor.getValue();
        assertEquals("DONATION", event.notificationType());
        assertNull(event.fairId());
        assertEquals("Doação amanhã", event.message());
        assertEquals(eventDate, event.eventDate());
        assertEquals(List.of(1, 7), event.recurrence());
    }


    @Test
    @DisplayName("Dado que existem notificações para hoje em uma única página, deve enviá-las e não iterar mais")
    void sendTodayNotificationsSinglePageShouldSendAndStop() {
        NotificationResponseDto dto = buildResponseDto("Notificação 1");
        PageResponseDto<NotificationResponseDto> page = buildPage(List.of(dto), 0, 1);

        when(notificationClient.findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class)))
                .thenReturn(page);

        notificationService.sendTodayNotifications();

        verify(mailService, times(1)).sendBulkNotifications(anyList());
        verify(notificationClient, times(1)).findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class));
    }

    @Test
    @DisplayName("Dado que existem notificações em múltiplas páginas, deve iterar até a última página")
    void sendTodayNotificationsMultiplePagesShouldIterateUntilLast() {
        NotificationResponseDto dto = buildResponseDto("Notificação");

        PageResponseDto<NotificationResponseDto> firstPage = buildPage(List.of(dto), 0, 3); // isLast=false
        PageResponseDto<NotificationResponseDto> secondPage = buildPage(List.of(dto), 1, 3); // isLast=false
        PageResponseDto<NotificationResponseDto> lastPage = buildPage(List.of(dto), 2, 3); // isLast=true

        when(notificationClient.findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class)))
                .thenReturn(firstPage)
                .thenReturn(secondPage)
                .thenReturn(lastPage);

        notificationService.sendTodayNotifications();

        verify(mailService, times(3)).sendBulkNotifications(anyList());
        verify(notificationClient, times(3)).findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class));
    }

    @Test
    @DisplayName("Dado que não existem notificações para hoje, não deve chamar o serviço de e-mail")
    void sendTodayNotificationsNoNotificationsShouldNotSendMail() {
        PageResponseDto<NotificationResponseDto> emptyPage = buildPage(List.of(), 0, 1);

        when(notificationClient.findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class)))
                .thenReturn(emptyPage);

        notificationService.sendTodayNotifications();

        verify(mailService, never()).sendBulkNotifications(anyList());
    }

    @Test
    @DisplayName("Dado que a primeira página está vazia com mais páginas existentes, deve parar imediatamente pelo isEmpty")
    void sendTodayNotificationsEmptyFirstPageShouldBreakImmediately() {
        PageResponseDto<NotificationResponseDto> emptyPage = buildPage(List.of(), 0, 3);

        when(notificationClient.findByRecurrenceDate(eq(LocalDate.now()), any(Pageable.class)))
                .thenReturn(emptyPage);

        notificationService.sendTodayNotifications();

        verify(notificationClient, times(1)).findByRecurrenceDate(any(), any());
        verify(mailService, never()).sendBulkNotifications(anyList());
    }

    private NotificationRequestDto buildRequest(NotificationType type, Long fairId, String message,
                                                LocalDate eventDate, List<Integer> recurrences) {
        NotificationRequestDto dto = new NotificationRequestDto();
        dto.setType(type);
        dto.setFairId(fairId);
        dto.setMessage(message);
        dto.setEventDate(eventDate);
        dto.setRecurrences(recurrences);
        return dto;
    }

    private NotificationResponseDto buildResponseDto(String message) {
        NotificationResponseDto dto = new NotificationResponseDto();
        dto.setType(NotificationType.DONATION);
        dto.setMessage(message);
        return dto;
    }

    private PageResponseDto<NotificationResponseDto> buildPage(List<NotificationResponseDto> data,
                                                               int pageNumber, int totalPages) {
        PageResponseDto<NotificationResponseDto> page = new PageResponseDto<>();
        page.setData(data);
        page.setPage(pageNumber);
        page.setTotalPages(totalPages);
        return page;
    }
}