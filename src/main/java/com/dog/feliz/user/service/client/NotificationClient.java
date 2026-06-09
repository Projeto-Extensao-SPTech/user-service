package com.dog.feliz.user.service.client;

import com.dog.feliz.user.service.controller.dto.NotificationResponseDto;
import com.dog.feliz.user.service.controller.dto.PageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDate;

@FeignClient(
        name = "notification-service",
        url = "${notification.service.url}"
)
public interface NotificationClient {
    @GetMapping("/notifications/{id}")
    NotificationResponseDto getById(@PathVariable("id") Long id);

    @GetMapping("/notifications")
    PageResponseDto<NotificationResponseDto> getAll(
            @RequestParam Integer page,
            @RequestParam Integer size
    );

    @GetMapping("/notifications/recurrence")
    PageResponseDto<NotificationResponseDto> findByRecurrenceDate(
            @RequestParam LocalDate date,
            @RequestParam(required = false) Pageable pageable
    );

    @DeleteMapping("/notifications/{id}")
    void delete(@PathVariable("id") Long id);
}