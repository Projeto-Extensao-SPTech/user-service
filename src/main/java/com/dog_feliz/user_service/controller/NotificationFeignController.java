package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationFeignController {

    private final NotificationClient notificationClient;

    public NotificationFeignController(NotificationClient notificationClient) {
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

    @GetMapping("/recurrence")
    public List<NotificationResponseDto> findByRecurrenceDate(@RequestParam LocalDate date){
        return notificationClient.findByRecurrenceDate(date);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationClient.delete(id);
    }
}