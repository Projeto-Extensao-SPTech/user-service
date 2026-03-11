package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.NotificationRequestDto;
import com.dog_feliz.user_service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping
    private ResponseEntity<Void> register(@RequestBody NotificationRequestDto notificationRequest) {
        notificationService.register(notificationRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
