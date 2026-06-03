package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.controller.dto.PageResponseDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
    public PageResponseDto<NotificationResponseDto> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return notificationClient.getAll(page, size);
    }

    @GetMapping("/recurrence")
    public PageResponseDto<NotificationResponseDto> findByRecurrenceDate(
            @RequestParam LocalDate date,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationClient.findByRecurrenceDate(date,pageable);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationClient.delete(id);
    }
}