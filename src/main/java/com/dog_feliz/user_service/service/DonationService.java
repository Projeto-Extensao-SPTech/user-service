package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.DonationRequestDto;
import com.dog_feliz.user_service.controller.dto.DonationResponseDto;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    // Método para criar doação
    public DonationResponseDto createDonation(DonationRequestDto requestDto, Long userId) {
        // Cria a entidade usando o construtor que fizemos antes
        DonationEntity newDonation = new DonationEntity(requestDto, userId);

        // Salva no banco
        DonationEntity savedDonation = donationRepository.save(newDonation);

        // Retorna convertendo para DTO (Igual ao seu UserService)
        return new DonationResponseDto(savedDonation);
    }


    public List<DonationResponseDto> getDonationsByUserId(Long userId) {
        // ERRO COMUM: donationRepository.findAllById(...) <- Isso causa o erro de Iterable

        // CORRETO: Chamar o método que criamos no passo 1
        List<DonationEntity> donations = donationRepository.findByUserId(userId.intValue());

        return donations.stream()
                .map(DonationResponseDto::new)
                .toList();
    }



}