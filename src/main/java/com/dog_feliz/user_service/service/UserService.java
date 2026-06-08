package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.NotificationType;
import com.dog_feliz.user_service.controller.dto.NotificationSendRequest;
import com.dog_feliz.user_service.controller.dto.UpdatePasswordRequestDto;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.shared.crypto.hash.StringHasher;
import com.dog_feliz.user_service.shared.exception.AddressNotFoundException;
import com.dog_feliz.user_service.shared.exception.ConflictUserException;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final NotificationService notificationService;
    private final StringHasher stringHasher;

    public UserService(UserRepository userRepository, AddressRepository addressRepository, BCryptPasswordEncoder passwordEncoder, ValidationService validationService, NotificationService notificationService, StringHasher stringHasher) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.notificationService = notificationService;
        this.stringHasher = stringHasher;
    }

    public List<UserResponseDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(UserResponseDto::new).toList();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id %d".formatted(id)));
    }

    public Boolean existsByMailAddress(String mail) {
        var findUserByEmail = userRepository.findByMailAddressHash(mail);

        return findUserByEmail.isPresent();
    }

    public void sendCodeForMail(String mail) {
        String generateRandomCode = Integer.toString(1000 + (int) (Math.random() * 9000));
        notificationService.send(
                new NotificationSendRequest(
                        NotificationType.UPDATE_PASSWORD,
                        mail,
                        generateRandomCode,
                        null
                )
        );

    }

    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        String mailAddressHash = stringHasher.hash(userRequestDto.getMailAddress());
        if (userRepository.findByMailAddressHash(mailAddressHash).isPresent())
            throw new ConflictUserException("Already exists an user with requested email");
        AddressEntity address = addressRepository.save(new AddressEntity(userRequestDto.getAddress()));
        UserEntity user = userRepository.save(new UserEntity(userRequestDto, address, passwordEncoder.encode(userRequestDto.getPassword()), mailAddressHash));
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        UserEntity userEntity = verifyUserId(id);
        Long addressId = userEntity.getAddress().getId();
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        if (addressEntity.isEmpty())
            throw new AddressNotFoundException("Address not found by id %d".formatted(addressId));

        AddressEntity addressUpdated = addressRepository.save(new AddressEntity(addressEntity.get().getId(), userRequestDto.getAddress()));
        UserEntity userUpdated = userRepository.save(new UserEntity(id, userRequestDto, addressUpdated));
        return new UserResponseDto(userUpdated);
    }

    public void updateReceiveNotification(Long userId, Boolean receiveNotification) {
        UserEntity userEntity = verifyUserId(userId);
        userEntity.setReceiveNotifications(receiveNotification);
        userRepository.save(userEntity);
    }

    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequest) {
        UserEntity userEntity = userRepository.findByPhone(updatePasswordRequest.phone()).orElseThrow(() -> new UserNotFoundException("User not found by requested phone and password, verify your credentials"));
        validationService.verifyIsValidUserId(userEntity.getId());
        userRepository.save(new UserEntity(userEntity, passwordEncoder.encode(updatePasswordRequest.password())));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserEntity> getUsersForNotification() {
        return userRepository.findByReceiveNotificationsTrue();
    }

    private UserEntity verifyUserId(Long userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (userEntity.isEmpty()) throw new UserNotFoundException("User not found by id %d".formatted(userId));
        return userEntity.get();
    }
}
