package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.DashboardFairKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardVolunteerKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog_feliz.user_service.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/month-most-interest")
    public DashboardFairKpiDto getMonthWithMostInterest() {
        return dashboardService.getMonthWithMostInterest();
    }

    @GetMapping("/location-most-interest")
    public DashboardFairKpiDto getLocationWithMostInterest() {
        return dashboardService.getLocationWithMostInterest();
    }

    @GetMapping("/day-most-volunteers")
    public DashboardVolunteerKpiDto getDayWithMostVolunteers() {
        return dashboardService.getDayWithMostVolunteers();
    }

    @GetMapping("/monthly-registrations")
    public List<DashboardMonthlyRegistrationDto> getMonthlyRegistrations() {
        return dashboardService.getMonthlyRegistrations();
    }
}
