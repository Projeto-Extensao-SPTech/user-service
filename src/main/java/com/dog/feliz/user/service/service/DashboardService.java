package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.DashboardFairKpiDto;
import com.dog.feliz.user.service.controller.dto.DashboardVolunteerKpiDto;
import com.dog.feliz.user.service.controller.dto.DashboardMonthlyRegistrationDto;
import com.dog.feliz.user.service.entity.VolunteerEntity;
import com.dog.feliz.user.service.repository.FairRepository;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.repository.VolunteerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DashboardService {

    private final FairRepository fairRepository;

    private final VolunteerRepository volunteerRepository;

    private final UserRepository userRepository;

    public DashboardService(
            FairRepository fairRepository,
            VolunteerRepository volunteerRepository,
            UserRepository userRepository
    ) {
        this.fairRepository = fairRepository;
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
    }

    public DashboardFairKpiDto getMonthWithMostInterest() {
        List<String> list = fairRepository.findMonthWithMostInterestRaw();
        String month = list.isEmpty() ? "-" : list.getFirst();
        return new DashboardFairKpiDto(month, null);
    }

    public DashboardFairKpiDto getLocationWithMostInterest() {
        Object[] raw = fairRepository.findLocationWithMostInterestRaw().stream().findFirst().orElse(null);
        if (raw == null) {
            return new DashboardFairKpiDto("-", 0L);
        }
        return new DashboardFairKpiDto((String) raw[0], ((Number) raw[1]).longValue());
    }

    public DashboardVolunteerKpiDto getDayWithMostVolunteers() {
        List<VolunteerEntity> volunteers = volunteerRepository.findAllByAvailableDateIsNotNull();

        int[] count = new int[7];
        for (VolunteerEntity volunteer : volunteers) {
            if (volunteer.getAvailableDate() != null) {
                count[volunteer.getAvailableDate().getDayOfWeek().getValue() - 1]++;
            }
        }

        int maxIndex = 0;
        for (int i = 1; i < 7; i++) {
            if (count[i] > count[maxIndex]) {
                maxIndex = i;
            }
        }

        String[] days = {"", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"};
        return new DashboardVolunteerKpiDto(days[maxIndex], (long) count[maxIndex]);
    }

    public List<DashboardMonthlyRegistrationDto> getMonthlyRegistrations() {
        List<Object[]> raw = userRepository.getMonthlyUserRegistrations();
        return raw.stream()
                .map(o -> new DashboardMonthlyRegistrationDto((String) o[0], ((Number) o[1]).longValue()))
                .toList();
    }
}
