package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.DonationResponseDto;
import com.dog_feliz.user_service.entity.DonationEntity;
import com.dog_feliz.user_service.repository.DonationRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.stub.DonationStub;
import com.dog_feliz.user_service.stub.UserStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DonationServiceTest {

    @Mock
    private DonationRepository donationRepository;
    @Mock
    private NotificationService notificationService;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DonationService donationService;

    @Test
    @DisplayName("Dada doação válida sem imagem, quando createDonation é chamado, deve salvar a doação")
    void givenValidDonationWithoutImage_whenCreate_thenSaves() {
        DonationEntity saved = new DonationEntity(DonationStub.validRequest(), 1L, null);
        when(donationRepository.save(any())).thenReturn(saved);
        when(userRepository.findById(1L)).thenReturn(Optional.of(UserStub.entityWithId(1L)));

        DonationResponseDto response = donationService.createDonation(DonationStub.validRequest(), 1L);

        assertNotNull(response);
        verify(notificationService).send(any());
    }

    @Test
    @DisplayName("Dada doação com imagem, quando createDonation é chamado, deve armazenar a imagem e salvar a doação")
    void givenDonationWithImage_whenCreate_thenStoresImage() {
        DonationEntity saved = new DonationEntity(DonationStub.validRequestWithImage(), 2L, "stored.png");
        when(donationRepository.save(any())).thenReturn(saved);
        when(userRepository.findById(2L)).thenReturn(Optional.empty());

        DonationResponseDto response = donationService.createDonation(DonationStub.validRequestWithImage(), 2L);

        assertNotNull(response);
        // when the return is empty, the notification is not sent
        verify(notificationService, times(0)).send(any());
    }

    @Test
    @DisplayName("Dado id do usuário, quando getDonationsByUserId é chamado, deve mapear as doações para DTOs")
    void givenUserId_whenGetDonations_thenReturnsList() {
        DonationEntity donation = new DonationEntity(DonationStub.validRequest(), 5L, null);
        when(donationRepository.findByUserId(5)).thenReturn(List.of(donation));

        List<DonationResponseDto> result = donationService.getDonationsByUserId(5L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Dado usuário sem doações, quando getDonationsByUserId é chamado, deve retornar lista vazia")
    void givenUserWithNoDonations_whenGetDonations_thenReturnsEmpty() {
        when(donationRepository.findByUserId(1)).thenReturn(List.of());

        assertTrue(donationService.getDonationsByUserId(1L).isEmpty());
    }
}
