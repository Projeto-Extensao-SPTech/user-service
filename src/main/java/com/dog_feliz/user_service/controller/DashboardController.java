package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.DashboardFairKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardVolunteerKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog_feliz.user_service.service.DashboardService;
import com.dog_feliz.user_service.service.ValidationService;
import com.dog_feliz.user_service.shared.exception.UnauthorizedUserException;
import com.dog_feliz.user_service.shared.utils.UserTokenValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final ValidationService validationService;

    public DashboardController(DashboardService dashboardService, UserTokenValidationUtils userTokenValidationUtils, ValidationService validationService) {
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
