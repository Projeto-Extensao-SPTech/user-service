package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.SponsorRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.SponsorEntity;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.SponsorRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.shared.exception.AddressNotFoundException;
import com.dog_feliz.user_service.shared.exception.SponsorNotFoundException;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

public class SponsorService {
    @Autowired
    private SponsorRepository sponsorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public List<SponsorResponseDto> getSponsors(){
        List<SponsorEntity> sponsors = sponsorRepository.findAll();
        return sponsors.stream().map(sponsorEntity -> new SponsorResponseDto(sponsorEntity)).toList();
    }

    public SponsorResponseDto getSponsorById(Long id){
        Optional<SponsorEntity> sponsorEntity = sponsorRepository.findById(id);
        if(sponsorEntity.isEmpty()) throw new SponsorNotFoundException("Patrocinador não encontrado com ID: %d".formatted(id));

        return new SponsorResponseDto(sponsorEntity.get());
    }

    public SponsorResponseDto addSponsor(SponsorRequestDto sponsorDto){
        UserEntity user = userRepository.findById(sponsorDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: %d".formatted(sponsorDto.getUserId())));

        AddressEntity address = addressRepository.findById(sponsorDto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado no ID: %d".formatted(sponsorDto.getAddressId())));

        SponsorEntity newSponsor = new SponsorEntity();
        newSponsor.setUser(user);
        newSponsor.setAddress(address);
        newSponsor.setName(sponsorDto.getName());
        newSponsor.setDocument(sponsorDto.getDocument());
        newSponsor.setDepartment(sponsorDto.getDepartment());

        return new SponsorResponseDto(newSponsor);
    }

    public SponsorResponseDto updateSponsor(Long id, SponsorRequestDto dto){
        SponsorEntity existingSponsor = sponsorRepository.findById(id)
                .orElseThrow(() -> new SponsorNotFoundException("Patrocinador não encontrado com ID: %d".formatted(id)));

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com ID: %d".formatted(dto.getUserId())));

        AddressEntity address = addressRepository.findById(dto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado no ID: %d".formatted(dto.getAddressId())));

        existingSponsor.setUser(user);
        existingSponsor.setAddress(address);
        existingSponsor.setName(dto.getName());
        existingSponsor.setDepartment(dto.getDepartment());
        existingSponsor.setDocument(dto.getDocument());

        SponsorEntity updateSponsor = sponsorRepository.save(existingSponsor);
        return new SponsorResponseDto(updateSponsor);
    }

    public HttpStatus deleteSponsor(Long id) {
        if (!sponsorRepository.existsById(id)) {
            throw new SponsorNotFoundException(
                    "Patrocinador não encontrado com ID: %d".formatted(id)
            );
        }
        sponsorRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
