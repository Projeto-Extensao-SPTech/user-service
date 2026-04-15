package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.client.NotificationClient;
import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.controller.dto.PageResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public PageResponseDto<NotificationResponseDto> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return notificationClient.getAll(pageable);
    }

    @GetMapping("/recurrence")
    public List<NotificationResponseDto> findByRecurrenceDate(
            @RequestParam LocalDate date,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {
        return notificationClient.findByRecurrenceDate(date);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationClient.delete(id);
    }
}