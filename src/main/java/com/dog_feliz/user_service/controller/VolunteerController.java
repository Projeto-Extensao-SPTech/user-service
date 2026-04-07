package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.VolunteerRequestDto;
import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.service.ValidationService;
import com.dog_feliz.user_service.service.VolunteerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final ValidationService validationService;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public VolunteerController(VolunteerService volunteerService, ValidationService validationService) {
        this.volunteerService = volunteerService;
        this.validationService = validationService;
    }

    @GetMapping
    public List<VolunteerResponseDto> getVolunteers() {
        List<VolunteerResponseDto> volunteers = volunteerService.getVolunteers();
        log.info("[GET_VOLUNTEERS] Volunteers fetched successfully total={}", volunteers.size());
        return volunteers;
    }

    @GetMapping("/{id}")
    public VolunteerResponseDto getVolunteerById(@PathVariable Long id) {
        VolunteerResponseDto volunteer = volunteerService.getVolunteerById(id);
        log.info("[GET_VOLUNTEER_BY_ID] Volunteer fetched successfully volunteerId={}", id);
        return volunteer;
    }

    @PostMapping
    public VolunteerResponseDto addVolunteer(@RequestBody VolunteerRequestDto dto) {
        VolunteerResponseDto response = volunteerService.addVolunteer(dto);
        log.info("[CREATE_VOLUNTEER] Volunteer created successfully");
        return response;
    }

    @PutMapping("/{id}")
    public VolunteerResponseDto updateVolunteer(@PathVariable Long id, @RequestBody VolunteerRequestDto dto) {
        validationService.verifyIsValidUserId(id);
        VolunteerResponseDto response = volunteerService.updateVolunteer(id, dto);
        log.info("[UPDATE_VOLUNTEER] Volunteer updated successfully volunteerId={}", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public void deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        log.info("[DELETE_VOLUNTEER] Volunteer deleted successfully volunteerId={}", id);
    }
}