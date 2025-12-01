package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    private ResponseEntity<NotificationResponseDto> register(@RequestBody NotificationRequestDto notificationRequest) {
        return ResponseEntity
                .status(201)
                .body(toResponse(notificationService.register(notificationRequest)));
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
        notificationService.deleteById(id);
    }

    private NotificationResponseDto toResponse(NotificationEntity notificationEntity) {
        return new NotificationResponseDto(notificationEntity);
    }
}
