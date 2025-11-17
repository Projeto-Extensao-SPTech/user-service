package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.SponsorResponseDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipRequestDto;
import com.dog_feliz.user_service.controller.dto.SponsorshipResponseDto;
import com.dog_feliz.user_service.entity.SponsorEntity;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.repository.SponsorRepository;
import com.dog_feliz.user_service.repository.SponsorshipRepository;
import com.dog_feliz.user_service.shared.exception.SponsorNotFoundException;
import com.dog_feliz.user_service.shared.exception.SponsorshipNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SponsorshipService {

    @Autowired
    private SponsorshipRepository sponsorshipRepository;

    @Autowired
    private SponsorRepository sponsorRepository;

    public List<SponsorshipResponseDto> getAllSponsorships(){
        List<SponsorshipEntity> sponsorship = sponsorshipRepository.findAll();
        return sponsorship.stream().map(sponsorshipEntity -> new SponsorshipResponseDto(sponsorshipEntity)).toList();
    }

    public SponsorshipResponseDto getSponsorshipBySponsorId(Long sponsorId){
        SponsorshipEntity sponsorship = sponsorshipRepository.findBySponsorId(sponsorId)
                .orElseThrow(() -> new SponsorshipNotFoundException("Nenhum vínculo de patrocínio encontrado para o SponsorID: %d".formatted(sponsorId)));

        return new SponsorshipResponseDto(sponsorship);
    }

    public SponsorshipResponseDto updateSponsorhipBySponsorId(Long id, SponsorshipRequestDto dto){
        SponsorshipEntity existingSponsorship = sponsorshipRepository.findBySponsorId(id)
                .orElseThrow(() -> new SponsorshipNotFoundException("Sponsorship não encontrado com o ID: %d".formatted(id)));

        SponsorEntity sponsor = sponsorRepository.findById(dto.getSponsorId())
                .orElseThrow(() -> new SponsorNotFoundException("Sponsor não encontrado com ID: %d".formatted(dto.getSponsorId())));

        existingSponsorship.setSponsor(sponsor);
        existingSponsorship.setType(dto.getType());
        existingSponsorship.setRecurrence(dto.getRecurrence());
        existingSponsorship.setDescription(dto.getDescription());
        existingSponsorship.setContext(dto.getContext());

        return new SponsorshipResponseDto(existingSponsorship);
    }

    public HttpStatus deleteSponsorship (Long id){
        if(!sponsorRepository.existsById(id)){
            throw new SponsorshipNotFoundException("Sponsorship não encontrada com ID: %d".formatted(id));
        }
        sponsorRepository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }
}
