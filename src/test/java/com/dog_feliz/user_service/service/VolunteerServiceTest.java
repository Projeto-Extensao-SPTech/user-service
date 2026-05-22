package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.VolunteerResponseDto;
import com.dog_feliz.user_service.entity.VolunteerEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.repository.VolunteerRepository;
import com.dog_feliz.user_service.service.mail.MailService;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import com.dog_feliz.user_service.shared.exception.VolunteerNotFoundException;
import com.dog_feliz.user_service.stub.UserStub;
import com.dog_feliz.user_service.stub.VolunteerStub;
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
class VolunteerServiceTest {

    @Mock
    private VolunteerRepository volunteerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MailService mailService;

    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    @DisplayName("Dados voluntários existentes, quando getVolunteers é chamado, deve retornar a lista")
    void givenVolunteers_whenGetVolunteers_thenReturnsList() {
        UserEntity user = UserStub.entityWithId(1L);
        when(volunteerRepository.findAll()).thenReturn(List.of(VolunteerStub.entityWithId(1L, user)));

        List<VolunteerResponseDto> result = volunteerService.getVolunteers();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Dado id de voluntário inexistente, quando getVolunteerById é chamado, deve lançar VolunteerNotFoundException")
    void givenUnknownId_whenGetVolunteerById_thenThrows() {
        when(volunteerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class, () -> volunteerService.getVolunteerById(99L));
    }

    @Test
    @DisplayName("Dado usuário inexistente, quando addVolunteer é chamado, deve lançar UserNotFoundException")
    void givenUnknownUser_whenAddVolunteer_thenThrowsUserNotFound() {
        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> volunteerService.addVolunteer(VolunteerStub.validRequest(5L)));
    }

    @Test
    @DisplayName("Dada requisição válida, quando addVolunteer é chamado, deve salvar o voluntário e enviar e-mail")
    void givenValidRequest_whenAddVolunteer_thenSavesAndNotifies() {
        UserEntity user = UserStub.entityWithId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(volunteerRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        VolunteerResponseDto response = volunteerService.addVolunteer(VolunteerStub.validRequest(1L));

        assertNotNull(response);
        verify(mailService).notifyVolunteer(any(VolunteerEntity.class));
    }

    @Test
    @DisplayName("Dado voluntário inexistente, quando updateVolunteer é chamado, deve lançar VolunteerNotFoundException")
    void givenUnknownVolunteer_whenUpdate_thenThrows() {
        when(volunteerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(VolunteerNotFoundException.class,
                () -> volunteerService.updateVolunteer(1L, VolunteerStub.validRequest(1L)));
    }

    @Test
    @DisplayName("Dado id de voluntário inexistente, quando deleteVolunteer é chamado, deve lançar VolunteerNotFoundException")
    void givenUnknownVolunteer_whenDelete_thenThrows() {
        when(volunteerRepository.existsById(7L)).thenReturn(false);

        assertThrows(VolunteerNotFoundException.class, () -> volunteerService.deleteVolunteer(7L));
    }

    @Test
    @DisplayName("Dado voluntário existente, quando deleteVolunteer é chamado, deve invocar a exclusão no repositório")
    void givenExistingVolunteer_whenDelete_thenDeletes() {
        when(volunteerRepository.existsById(1L)).thenReturn(true);

        volunteerService.deleteVolunteer(1L);

        verify(volunteerRepository).deleteById(1L);
    }
}
