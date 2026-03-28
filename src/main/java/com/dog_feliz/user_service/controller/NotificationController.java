package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.service.NotificationService;
import com.dog_feliz.user_service.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ValidationService validationService;
    private static final Logger log = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService, ValidationService validationService) {
        this.notificationService = notificationService;
        this.validationService = validationService;
    }

    @PostMapping
    private ResponseEntity<NotificationResponseDto> register(@RequestBody NotificationRequestDto notificationRequest) {
        validationService.verifyIsAdminUser();
        NotificationResponseDto response = toResponse(notificationService.register(notificationRequest));

        log.info("[REGISTER_NOTIFICATION] Notification registered successfully");
        return ResponseEntity
                .status(202)
                .body(response);
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/send-today-notifications")
    private void sendTodayNotifications() {
        notificationService.sendTodayNotifications();

        log.info("[SEND_TODAY_NOTIFICATIONS] Notifications sent successfully");
    }

    @GetMapping
    private ResponseEntity<List<NotificationResponseDto>> getAllNotifications() {
        List<NotificationEntity> notifications = notificationService.getAllNotifications();

        if (notifications.isEmpty()) {
            log.warn("[GET_ALL_NOTIFICATIONS] No notifications found");
            return ResponseEntity.status(204).body(null);
        }

        log.info("[GET_ALL_NOTIFICATIONS] Notifications fetched successfully total={}", notifications.size());
        return ResponseEntity.ok(
                notifications
                        .stream()
                        .map(notification -> toResponse(notification))
                        .toList()
        );
    }

    @GetMapping("/date/{date}")
    private ResponseEntity<List<NotificationResponseDto>> getByRecurrenceDate(@PathVariable LocalDate date) {
        List<NotificationEntity> notifications = notificationService.getByRecurrenceDate(date);

        if (notifications.isEmpty()) {
            log.warn("[GET_NOTIFICATIONS_BY_DATE] No notifications found date={}", date);
            return ResponseEntity.status(204).body(null);
        }

        log.info("[GET_NOTIFICATIONS_BY_DATE] Notifications fetched successfully date={} total={}", date, notifications.size());
        return ResponseEntity.ok(
                notifications
                        .stream()
                        .map(notification -> toResponse(notification))
                        .toList()
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<NotificationResponseDto> getById(@PathVariable Long id) {
        NotificationResponseDto response = toResponse(notificationService.getById(id));

        log.info("[GET_NOTIFICATION_BY_ID] Notification fetched successfully notificationId={}", id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        notificationService.deleteById(id);

        log.info("[DELETE_NOTIFICATION] Notification deleted successfully notificationId={}", id);
    }

    private NotificationResponseDto toResponse(NotificationEntity notificationEntity) {
        return new NotificationResponseDto(notificationEntity);
    }
}