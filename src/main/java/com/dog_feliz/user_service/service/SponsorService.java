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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {
    private final SponsorRepository sponsorRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public SponsorService(SponsorRepository sponsorRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.sponsorRepository = sponsorRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

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
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

        AddressEntity address = addressRepository.findById(sponsorDto.getAddressId())
                .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado"));

        SponsorEntity newSponsor = new SponsorEntity(sponsorDto, user, address);
        SponsorEntity saved = sponsorRepository.save(newSponsor);

        return new SponsorResponseDto(saved);
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

    public void deleteSponsor(Long id) {
        if (!sponsorRepository.existsById(id)) {
            throw new SponsorNotFoundException(
                    "Patrocinador não encontrado com ID: %d".formatted(id)
            );
        }
        sponsorRepository.deleteById(id);
    }
}
