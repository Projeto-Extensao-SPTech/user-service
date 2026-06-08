package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.UpdatePasswordRequestDto;
import com.dog.feliz.user.service.controller.dto.UserResponseDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import com.dog.feliz.user.service.shared.exception.AddressNotFoundException;
import com.dog.feliz.user.service.shared.exception.ConflictUserException;
import com.dog.feliz.user.service.shared.exception.UserNotFoundException;
import com.dog.feliz.user.service.stub.AddressStub;
import com.dog.feliz.user.service.stub.UserStub;
import com.dog.feliz.user.service.support.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private ValidationService validationService;
    @Mock
    private StringHasher stringHasher;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        lenient().when(stringHasher.hash(anyString())).thenReturn("hashed-mail");
        lenient().when(passwordEncoder.encode(anyString())).thenReturn("encoded-password");
    }

    @Test
    @DisplayName("Dado usuários existentes, quando getUsers é chamado, deve retornar todos os usuários")
    void givenUsers_whenGetUsers_thenReturnsAll() {
        UserEntity user = UserStub.entityWithId(1L);
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserResponseDto> result = userService.getUsers();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Dado id válido, quando getUserById é chamado, deve retornar o usuário")
    void givenValidId_whenGetUserById_thenReturnsUser() {
        UserEntity user = UserStub.entityWithId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Dado id inexistente, quando getUserById é chamado, deve lançar UserNotFoundException")
    void givenUnknownId_whenGetUserById_thenThrowsNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99L));
    }

    @Test
    @DisplayName("Dado e-mail duplicado, quando addUser é chamado, deve lançar ConflictUserException")
    void givenDuplicateEmail_whenAddUser_thenThrowsConflict() {
        when(userRepository.findByMailAddressHash("hashed-mail")).thenReturn(Optional.of(UserStub.entityWithId(1L)));

        assertThrows(ConflictUserException.class, () -> userService.addUser(UserStub.validRequest()));
    }

    @Test
    @DisplayName("Dada requisição válida, quando addUser é chamado, deve persistir o usuário sem privilégio de admin")
    void givenValidRequest_whenAddUser_thenPersistsUser() {
        AddressEntity address = AddressStub.entityWithId(1L);
        UserEntity saved = UserStub.entityWithId(2L, address);

        when(userRepository.findByMailAddressHash("hashed-mail")).thenReturn(Optional.empty());
        when(addressRepository.save(any())).thenReturn(address);
        when(userRepository.save(any(UserEntity.class))).thenReturn(saved);

        UserResponseDto result = userService.addUser(UserStub.validRequest(true));

        assertNotNull(result);
        verify(userRepository).save(argThat(user -> Boolean.FALSE.equals(user.getIsAdmin())));
    }

    @Test
    @DisplayName("Dado endereço inexistente na atualização, quando updateUser é chamado, deve lançar AddressNotFoundException")
    void givenMissingAddress_whenUpdateUser_thenThrowsAddressNotFound() {
        UserEntity user = UserStub.entityWithId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(AddressNotFoundException.class,
                () -> userService.updateUser(1L, UserStub.validRequest()));
    }

    @Test
    @DisplayName("Dado telefone inexistente, quando updatePassword é chamado, deve lançar UserNotFoundException")
    void givenUnknownPhone_whenUpdatePassword_thenThrowsNotFound() {
        when(userRepository.findByPhone(TestConstants.VALID_PHONE)).thenReturn(Optional.empty());

        var request = new UpdatePasswordRequestDto(TestConstants.VALID_PHONE, TestConstants.VALID_PASSWORD);

        assertThrows(UserNotFoundException.class, () -> userService.updatePassword(request));
    }

    @Test
    @DisplayName("Dado telefone válido, quando updatePassword é chamado, deve atualizar a senha do usuário autenticado")
    void givenValidPhone_whenUpdatePassword_thenUpdatesPassword() {
        UserEntity user = UserStub.entityWithId(5L);
        when(userRepository.findByPhone(TestConstants.VALID_PHONE)).thenReturn(Optional.of(user));

        var request = new UpdatePasswordRequestDto(TestConstants.VALID_PHONE, "NovaSenha@99");
        userService.updatePassword(request);

        verify(validationService).verifyIsValidUserId(5L);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("Dada consulta por telefone, quando existsByPhone é chamado, deve retornar o resultado do repositório")
    void givenPhone_whenExistsByPhone_thenDelegatesToRepository() {
        when(userRepository.existsByPhone(TestConstants.VALID_PHONE)).thenReturn(true);

        assertTrue(userService.existsByPhone(TestConstants.VALID_PHONE));
    }

    @Test
    @DisplayName("Dada preferência de notificação, quando updateReceiveNotification é chamado, deve atualizar o usuário")
    void givenUser_whenUpdateReceiveNotification_thenUpdatesFlag() {
        UserEntity user = UserStub.entityWithId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.updateReceiveNotification(1L, false);

        assertFalse(user.getReceiveNotifications());
        verify(userRepository).save(user);
    }

    @Test
    @DisplayName("Dado id do usuário, quando deleteUser é chamado, deve invocar a exclusão no repositório")
    void givenUserId_whenDeleteUser_thenDeletes() {
        userService.deleteUser(3L);
        verify(userRepository).deleteById(3L);
    }
}
