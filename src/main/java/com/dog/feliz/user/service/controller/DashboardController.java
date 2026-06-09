package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.controller.dto.DashboardFairKpiDto;
import com.dog.feliz.user.service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog.feliz.user.service.controller.dto.DashboardVolunteerKpiDto;
import com.dog.feliz.user.service.service.DashboardService;
import com.dog.feliz.user.service.service.ValidationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    private final ValidationService validationService;

    public DashboardController(
            DashboardService dashboardService,
            ValidationService validationService
    ) {
        this.dashboardService = dashboardService;
        this.validationService = validationService;
    }

    @GetMapping("/month-most-interest")
    public DashboardFairKpiDto getMonthWithMostInterest() {
        validationService.verifyIsAdminUser();
        return dashboardService.getMonthWithMostInterest();
    }

    @GetMapping("/location-most-interest")
    public DashboardFairKpiDto getLocationWithMostInterest() {
        validationService.verifyIsAdminUser();
        return dashboardService.getLocationWithMostInterest();
    }

    @GetMapping("/day-most-volunteers")
    public DashboardVolunteerKpiDto getDayWithMostVolunteers() {
        validationService.verifyIsAdminUser();
        return dashboardService.getDayWithMostVolunteers();
    }

    @GetMapping("/monthly-registrations")
    public List<DashboardMonthlyRegistrationDto> getMonthlyRegistrations() {
        validationService.verifyIsAdminUser();
        return dashboardService.getMonthlyRegistrations();
    }
}
