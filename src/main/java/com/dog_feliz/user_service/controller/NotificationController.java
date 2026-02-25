package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.service.NotificationService;
import com.dog_feliz.user_service.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final ValidationService validationService;

    public NotificationController(NotificationService notificationService, ValidationService validationService) {
        this.notificationService = notificationService;
        this.validationService = validationService;
    }

    @PostMapping
    private ResponseEntity<NotificationResponseDto> register(@RequestBody NotificationRequestDto notificationRequest) {
        validationService.verifyIsAdminUser();
        return ResponseEntity
                .status(201)
                .body(toResponse(notificationService.register(notificationRequest)));
    }

    @RequestMapping(method = RequestMethod.HEAD, path = "/send-today-notifications")
    private void sendTodayNotifications() {
        notificationService.sendTodayNotifications();
    }

    @GetMapping
    private ResponseEntity<List<NotificationResponseDto>> getAllNotifications() {
        List<NotificationEntity> notifications = notificationService.getAllNotifications();
        return notifications.isEmpty()
                ? ResponseEntity.status(204).body(null)
                : ResponseEntity.ok(
                notifications
                        .stream()
                        .map(notification -> toResponse(notification))
                        .toList()
        );
    }

    @GetMapping("/date/{date}")
    private ResponseEntity<List<NotificationResponseDto>> getByRecurrenceDate(@PathVariable LocalDate date) {
        List<NotificationEntity> notifications = notificationService.getByRecurrenceDate(date);
        return notifications.isEmpty()
                ? ResponseEntity.status(204).body(null)
                : ResponseEntity.ok(
                        notifications
                                .stream()
                                .map(notification -> toResponse(notification))
                                .toList()
        );
    }

    @GetMapping("/{id}")
    private ResponseEntity<NotificationResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toResponse(notificationService.getById(id)));
    }

    @DeleteMapping("/{id}")
    private void deleteById(@PathVariable Long id) {
        validationService.verifyIsAdminUser();
        notificationService.deleteById(id);
    }

    private NotificationResponseDto toResponse(NotificationEntity notificationEntity) {
        return new NotificationResponseDto(notificationEntity);
    }
}
