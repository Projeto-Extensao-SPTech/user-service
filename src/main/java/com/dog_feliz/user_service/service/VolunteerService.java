package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.NotificationSendRequest;
import com.dog_feliz.user_service.controller.dto.NotificationType;
import com.dog_feliz.user_service.controller.dto.VolunteerRequestDto;
import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.repository.VolunteerRepository;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import com.dog_feliz.user_service.shared.exception.VolunteerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public VolunteerService(VolunteerRepository volunteerRepository, UserRepository userRepository, NotificationService notificationService) {
        this.volunteerRepository = volunteerRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public List<VolunteerResponseDto> getVolunteers() {
        return volunteerRepository.findAll()
                .stream()
                .map(VolunteerResponseDto::new)
                .toList();
    }

    public VolunteerEntity getVolunteerById(Long id) {
        return volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id)));
    }

    public VolunteerResponseDto addVolunteer(VolunteerRequestDto dto) {
        UserEntity userEntity = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found by id %d".formatted(dto.getUserId())));

        VolunteerEntity volunteer = new VolunteerEntity(
                dto.getMessage(),
                dto.getAvailableDate(),
                userEntity
        );

        Long volunteerId = volunteerRepository.save(volunteer).getId();

        notificationService.send(
                new NotificationSendRequest(
                        NotificationType.VOLUNTEER,
                        null,
                        null,
                        volunteerId
                )
        );

        return new VolunteerResponseDto(volunteer);
    }

    public VolunteerResponseDto updateVolunteer(Long id, VolunteerRequestDto dto) {

        VolunteerEntity existing = volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id)));

        UserEntity userEntity = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found by id %d".formatted(dto.getUserId())));


        VolunteerEntity updated = new VolunteerEntity(
                existing.getId(),
                dto.getMessage(),
                dto.getAvailableDate(),
                userEntity
        );

        volunteerRepository.save(updated);

        return new VolunteerResponseDto(updated);
    }

    public void deleteVolunteer(Long id) {
        if (!volunteerRepository.existsById(id)) {
            throw new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id));
        }
        volunteerRepository.deleteById(id);
    }
}
