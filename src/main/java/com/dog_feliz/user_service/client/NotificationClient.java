package com.dog_feliz.user_service.client;

import com.dog_feliz.user_service.controller.dto.NotificationResponseDto;
import com.dog_feliz.user_service.controller.dto.PageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(
        name = "notification-service",
        url = "${notification.service.url}"
)
public interface NotificationClient {
    @GetMapping("/notifications/{id}")
    NotificationResponseDto getById(@PathVariable("id") Long id);

    @GetMapping("/notifications")
    PageResponseDto<NotificationResponseDto> getAll(@SpringQueryMap Pageable pageable);

    @GetMapping("/notifications/recurrence")
    List<NotificationResponseDto> findByRecurrenceDate(@RequestParam LocalDate date);

    @DeleteMapping("/notifications/{id}")
    void delete(@PathVariable("id") Long id);
}