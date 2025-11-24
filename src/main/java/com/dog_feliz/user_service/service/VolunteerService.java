package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.VolunteerRequestDto;
import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.VolunteerRepository;
import com.dog_feliz.user_service.shared.exception.AddressNotFoundException;
import com.dog_feliz.user_service.shared.exception.VolunteerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VolunteerService {

    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private AddressRepository addressRepository;


    public List<VolunteerResponseDto> getVolunteers() {
        List<VolunteerEntity> volunteers = volunteerRepository.findAll();
        return volunteers.stream()
                .map(VolunteerResponseDto::new)
                .toList();
    }



    public VolunteerResponseDto getVolunteerById(Long id) {
        VolunteerEntity volunteer = volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id)));

        return new VolunteerResponseDto(volunteer);
    }

    public VolunteerResponseDto addVolunteer(VolunteerRequestDto dto) {
        AddressEntity address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException("Address not found by id %d".formatted(dto.getAddressId())));

        VolunteerEntity volunteer = new VolunteerEntity(
                null,
                dto.getMessage(),
                dto.getAvailableDate(),
                address
        );

        volunteerRepository.save(volunteer);
        return new VolunteerResponseDto(volunteer);
    }

    public VolunteerResponseDto updateVolunteer(Long id, VolunteerRequestDto dto) {
        VolunteerEntity volunteer = volunteerRepository.findById(id)
                .orElseThrow(() ->
                        new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id)));

        AddressEntity address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() ->
                        new AddressNotFoundException("Address not found by id %d".formatted(dto.getAddressId())));

        volunteer.setMessage(dto.getMessage());
        volunteer.setAvailableDate(dto.getAvailableDate());
        volunteer.setAddress(address);

        volunteerRepository.save(volunteer);

        return new VolunteerResponseDto(volunteer);
    }


    public void deleteVolunteer(Long id) {
        if (!volunteerRepository.existsById(id)) {
            throw new VolunteerNotFoundException("Volunteer not found by id %d".formatted(id));
        }
        volunteerRepository.deleteById(id);
    }
}
