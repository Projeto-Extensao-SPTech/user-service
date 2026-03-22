package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.VolunteerRequestDto;
import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.service.ValidationService;
import com.dog_feliz.user_service.service.VolunteerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
public class VolunteerController {

    private final VolunteerService volunteerService;
    private final ValidationService validationService;

    public VolunteerController(VolunteerService volunteerService, ValidationService validationService) {
        this.volunteerService = volunteerService;
        this.validationService = validationService;
    }

    @GetMapping
    public List<VolunteerResponseDto> getVolunteers() {
        return volunteerService.getVolunteers();
    }

    @GetMapping("/{id}")
    public VolunteerResponseDto getVolunteerById(@PathVariable Long id) {
        return volunteerService.getVolunteerById(id);
    }

    @PostMapping
    public VolunteerResponseDto addVolunteer(@RequestBody VolunteerRequestDto dto) {
        return volunteerService.addVolunteer(dto);
    }

    @PutMapping("/{id}")
    public VolunteerResponseDto updateVolunteer(@PathVariable Long id, @RequestBody VolunteerRequestDto dto) {
        validationService.verifyIsValidUserId(id);
        return volunteerService.updateVolunteer(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
    }
}
