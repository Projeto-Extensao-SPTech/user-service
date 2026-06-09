package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.DashboardFairKpiDto;
import com.dog.feliz.user.service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog.feliz.user.service.controller.dto.DashboardVolunteerKpiDto;
import com.dog.feliz.user.service.entity.VolunteerEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.repository.VolunteerRepository;
import com.dog.feliz.user.service.stub.UserStub;
import com.dog.feliz.user.service.stub.VolunteerStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private FairRepository fairRepository;
    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    @DisplayName("Dados ausentes de feiras, quando getMonthWithMostInterest é chamado, deve retornar valor padrão")
    void givenNoDataWhenGetMonthWithMostInterestThenReturnsPlaceholder() {
        when(fairRepository.findMonthWithMostInterestRaw()).thenReturn(List.of());

        DashboardFairKpiDto result = dashboardService.getMonthWithMostInterest();

        assertEquals("-", result.label());
    }

    @Test
    @DisplayName("Dados de feiras existentes, quando getMonthWithMostInterest é chamado, deve retornar o mês com mais interesse")
    void givenDataWhenGetMonthWithMostInterestThenReturnsMonth() {
        when(fairRepository.findMonthWithMostInterestRaw()).thenReturn(List.of("Janeiro"));

        DashboardFairKpiDto result = dashboardService.getMonthWithMostInterest();

        assertEquals("Janeiro", result.label());
    }

    @Test
    @DisplayName("Dados ausentes de localização, quando getLocationWithMostInterest é chamado, deve retornar valor padrão")
    void givenNoLocationDataWhenGetLocationThenReturnsPlaceholder() {
        when(fairRepository.findLocationWithMostInterestRaw()).thenReturn(List.of());

        DashboardFairKpiDto result = dashboardService.getLocationWithMostInterest();

        assertEquals("-", result.label());
        assertEquals(0L, result.total());
    }

    @Test
    @DisplayName("Dados voluntários em dias distintos, quando getDayWithMostVolunteers é chamado, deve retornar o dia com mais voluntários")
    void givenVolunteersWhenGetDayWithMostVolunteersThenReturnsBusiestDay() {
        UserEntity user = UserStub.entityWithId(1L);
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate tuesday = LocalDate.now().with(DayOfWeek.TUESDAY);

        VolunteerEntity v1 = VolunteerStub.entityWithId(1L, user);
        ReflectionSetAvailableDate(v1, monday);
        VolunteerEntity v2 = VolunteerStub.entityWithId(2L, user);
        ReflectionSetAvailableDate(v2, monday);
        VolunteerEntity v3 = VolunteerStub.entityWithId(3L, user);
        ReflectionSetAvailableDate(v3, tuesday);

        when(volunteerRepository.findAllByAvailableDateIsNotNull())
                .thenReturn(List.of(v1, v2, v3));

        DashboardVolunteerKpiDto result = dashboardService.getDayWithMostVolunteers();

        assertEquals("", result.day());
        assertEquals(2L, result.total());
    }

    @Test
    @DisplayName("Dados de cadastros mensais, quando getMonthlyRegistrations é chamado, deve retornar DTOs mapeados")
    void givenRegistrationsWhenGetMonthlyRegistrationsThenReturnsMapped() {
        when(userRepository.getMonthlyUserRegistrations())
                .thenReturn(List.<Object[]>of(new Object[]{"2025-01", 10L}));

        List<DashboardMonthlyRegistrationDto> result = dashboardService.getMonthlyRegistrations();

        assertEquals(1, result.size());
        assertEquals("2025-01", result.getFirst().month());
        assertEquals(10L, result.getFirst().total());
    }

    private static void ReflectionSetAvailableDate(VolunteerEntity volunteer, LocalDate date) {
        org.springframework.test.util.ReflectionTestUtils.setField(volunteer, "availableDate", date);
    }
}
