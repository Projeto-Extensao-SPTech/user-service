package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.notification.NotificationRequestDto;
import com.dog_feliz.user_service.controller.dto.notification.NotificationResponseDto;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    private ResponseEntity<NotificationResponseDto> register(@RequestBody NotificationRequestDto notificationRequest) {
        return ResponseEntity.status(201).body(notificationService.register(notificationRequest));
    }

    @GetMapping
    private ResponseEntity<List<NotificationResponseDto>> getTodayNotifications() {
        List<NotificationResponseDto> notifications = notificationService.getTodayNotifications();
        return notifications.isEmpty() ? ResponseEntity.status(204).body(null) : ResponseEntity.ok(notifications);
    }
}
