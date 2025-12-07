package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.DashboardFairKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardVolunteerKpiDto;
import com.dog_feliz.user_service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.repository.FairRepository;
import com.dog_feliz.user_service.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class DashboardService {

    private final FairRepository fairRepository;
    private final VolunteerRepository volunteerRepository;

    public DashboardService(FairRepository fairRepository, VolunteerRepository volunteerRepository) {
        this.fairRepository = fairRepository;
        this.volunteerRepository = volunteerRepository;
    }


    public DashboardFairKpiDto getMonthWithMostInterest() {
        Object[] raw = fairRepository.findMonthWithMostInterestRaw().stream().findFirst().orElse(null);
        if (raw == null) return new DashboardFairKpiDto("-", 0L);
        return new DashboardFairKpiDto((String) raw[0], ((Number) raw[1]).longValue());
    }


    public DashboardFairKpiDto getLocationWithMostInterest() {
        Object[] raw = fairRepository.findLocationWithMostInterestRaw().stream().findFirst().orElse(null);
        if (raw == null) return new DashboardFairKpiDto("-", 0L);
        return new DashboardFairKpiDto((String) raw[0], ((Number) raw[1]).longValue());
    }


    public DashboardVolunteerKpiDto getDayWithMostVolunteers() {
        List<VolunteerEntity> volunteers = volunteerRepository.findAllByAvailableDateIsNotNull();

        int[] count = new int[7];
        for (VolunteerEntity v : volunteers) {
            if (v.getAvailableDate() != null) {
                count[v.getAvailableDate().getDayOfWeek().getValue() - 1]++;
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < 7; i++) {
            if (count[i] > count[maxIndex]) maxIndex = i;
        }

        String[] days = {"","Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
        return new DashboardVolunteerKpiDto(days[maxIndex], (long) count[maxIndex]);
    }


    public List<DashboardMonthlyRegistrationDto> getMonthlyRegistrations() {
        List<Object[]> raw = volunteerRepository.getMonthlyRegistrationsRaw();
        return raw.stream()
                .map(o -> new DashboardMonthlyRegistrationDto((String) o[0], ((Number) o[1]).longValue()))
                .toList();
    }
}
