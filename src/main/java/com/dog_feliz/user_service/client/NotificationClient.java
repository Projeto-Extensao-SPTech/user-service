package com.dog_feliz.user_service.client;

import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.controller.dto.PageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@FeignClient(
        name = "notification-service",
        url = "${notification.service.url:http://localhost:9999}"
)
public interface NotificationClient {
    @GetMapping("/notifications/{id}")
    NotificationResponseDto getById(@PathVariable("id") Long id);

    @GetMapping("/notifications")
    PageResponseDto<NotificationResponseDto> getAll(@SpringQueryMap Pageable pageable);

    @GetMapping("/notifications/recurrence")
    PageResponseDto<NotificationResponseDto> findByRecurrenceDate(@RequestParam LocalDate date, @RequestParam(required = false) Pageable pageable);

    @DeleteMapping("/notifications/{id}")
    void delete(@PathVariable("id") Long id);
}