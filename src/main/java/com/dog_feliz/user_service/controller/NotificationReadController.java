package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationReadController {

    private final NotificationClient notificationClient;

    public NotificationReadController(NotificationClient notificationClient) {
        this.notificationClient = notificationClient;
    }

    @GetMapping("/{id}")
    public NotificationResponseDto getById(@PathVariable Long id) {
        return notificationClient.getById(id);
    }

    @GetMapping
    public List<NotificationResponseDto> getAll() {
        return notificationClient.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationClient.delete(id);
    }
}