package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.SponsorshipResponseDto;
import com.dog_feliz.user_service.entity.SponsorshipEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.SponsorshipRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.service.mail.MailService;
import com.dog_feliz.user_service.shared.exception.SponsorshipNotFoundException;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import com.dog_feliz.user_service.stub.SponsorshipStub;
import com.dog_feliz.user_service.stub.UserStub;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SponsorshipServiceTest {

    @Mock
    private SponsorshipRepository sponsorshipRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MailService mailService;

    @InjectMocks
    private SponsorshipService sponsorshipService;

    @Test
    @DisplayName("Dados patrocínios existentes, quando getAllSponsorships é chamado, deve retornar a lista")
    void givenSponsorships_whenGetAll_thenReturnsList() {
        SponsorshipEntity entity = new SponsorshipEntity();
        entity.setId(1L);
        when(sponsorshipRepository.findAll()).thenReturn(List.of(entity));

        assertEquals(1, sponsorshipService.getAllSponsorships().size());
    }

    @Test
    @DisplayName("Dado id de patrocínio inexistente, quando getSponsorshipById é chamado, deve lançar SponsorshipNotFoundException")
    void givenUnknownId_whenGetById_thenThrows() {
        when(sponsorshipRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(SponsorshipNotFoundException.class, () -> sponsorshipService.getSponsorshipById(99L));
    }

    @Test
    @DisplayName("Dado patrocinador inexistente, quando addSponsorship é chamado, deve lançar UserNotFoundException")
    void givenUnknownSponsor_whenAdd_thenThrowsUserNotFound() {
        when(userRepository.findById(3L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> sponsorshipService.addSponsorship(SponsorshipStub.validRequest(3L)));
    }

    @Test
    @DisplayName("Dado patrocinador válido, quando addSponsorship é chamado, deve salvar o patrocínio e enviar e-mail")
    void givenValidSponsor_whenAdd_thenSavesAndNotifies() {
        UserEntity sponsor = UserStub.entityWithId(1L);
        SponsorshipEntity saved = new SponsorshipEntity();
        saved.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(sponsor));
        when(sponsorshipRepository.save(any())).thenReturn(saved);

        SponsorshipResponseDto response = sponsorshipService.addSponsorship(SponsorshipStub.validRequest(1L));

        assertNotNull(response);
        verify(mailService).notifySponsorship(any());
    }

    @Test
    @DisplayName("Dado patrocínio inexistente, quando deleteSponsorship é chamado, deve lançar SponsorshipNotFoundException")
    void givenUnknown_whenDelete_thenThrows() {
        when(sponsorshipRepository.existsById(5L)).thenReturn(false);

        assertThrows(SponsorshipNotFoundException.class, () -> sponsorshipService.deleteSponsorship(5L));
    }
}
