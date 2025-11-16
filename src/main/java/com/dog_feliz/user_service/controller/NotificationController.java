package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.notification.NotificationResponseDto;
import com.dog_feliz.user_service.entity.notification.NotificationEntity;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ResponseEntity<List<NotificationResponseDto>> getTodayNotifications() {
        List<NotificationEntity> notifications = notificationService.getTodayNotifications();
        return notifications.isEmpty()
                ? ResponseEntity.status(204).body(null)
                : ResponseEntity.ok(
                        notifications
                                .stream()
                                .map(notification -> toResponse(notification))
                                .toList()
        );
    }

    private NotificationResponseDto toResponse(NotificationEntity notificationEntity) {
        return new NotificationResponseDto(notificationEntity);
    }
}
