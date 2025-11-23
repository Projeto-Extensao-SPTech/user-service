package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.SponsorshipRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipResponseDto;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.repository.SponsorshipRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.shared.exception.SponsorshipNotFoundException;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorshipService {

    @Autowired
    private SponsorshipRepository sponsorshipRepository;

    @Autowired
    private UserRepository userRepository;

    public List<SponsorshipResponseDto> getAllSponsorships() {
        List<SponsorshipEntity> sponsorship = sponsorshipRepository.findAll();
        return sponsorship.stream().map(sponsorshipEntity -> new SponsorshipResponseDto(sponsorshipEntity)).toList();
    }

    public SponsorshipResponseDto getSponsorshipById(Long id) {
        SponsorshipEntity sponsorship = sponsorshipRepository.findById(id)
                .orElseThrow(() ->
                        new SponsorshipNotFoundException("Sponsorship não encontrado com o ID: %d".formatted(id))
                );

        return new SponsorshipResponseDto(sponsorship);
    }

    public SponsorshipResponseDto getSponsorshipBySponsorId(Long sponsorId) {
        SponsorshipEntity sponsorship = sponsorshipRepository.findBySponsorId(sponsorId)
                .orElseThrow(() ->
                        new SponsorshipNotFoundException("Nenhum vínculo encontrado para o sponsorId: %d".formatted(sponsorId))
                );

        return new SponsorshipResponseDto(sponsorship);
    }

    public SponsorshipResponseDto addSponsorship(SponsorshipRequestDto dto) {
        UserEntity sponsor = userRepository.findById(dto.getSponsorId())
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário patrocinador não encontrado: %d".formatted(dto.getSponsorId()))
                );

        SponsorshipEntity sponsorship = new SponsorshipEntity(sponsor, dto);
        sponsorship.setSponsor(sponsor);
        sponsorship.setType(dto.getType());
        sponsorship.setRecurrence(dto.getRecurrence());
        sponsorship.setDescription(dto.getDescription());
        sponsorship.setDepartment(dto.getDepartment());

        SponsorshipEntity saved = sponsorshipRepository.save(sponsorship);

        return new SponsorshipResponseDto(saved);
    }

    public SponsorshipResponseDto updateSponsorship(Long id, SponsorshipRequestDto dto) {
        SponsorshipEntity existing = sponsorshipRepository.findById(id)
                .orElseThrow(() ->
                        new SponsorshipNotFoundException("Sponsorship não encontrado com o ID: %d".formatted(id))
                );

        UserEntity sponsor = userRepository.findById(dto.getSponsorId())
                .orElseThrow(() ->
                        new UserNotFoundException("Usuário não encontrado: %d".formatted(dto.getSponsorId()))
                );

        existing.setSponsor(sponsor);
        existing.setType(dto.getType());
        existing.setRecurrence(dto.getRecurrence());
        existing.setDescription(dto.getDescription());
        existing.setDepartment(dto.getDepartment());

        SponsorshipEntity saved = sponsorshipRepository.save(existing);

        return new SponsorshipResponseDto(saved);
    }

    public void deleteSponsorship(Long id) {
        if (!sponsorshipRepository.existsById(id)) {
            throw new SponsorshipNotFoundException("Sponsorship não encontrada com ID: %d".formatted(id));
        }

        sponsorshipRepository.deleteById(id);
    }

}
