package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.UpdatePasswordRequestDto;
import com.dog.feliz.user.service.controller.dto.UserRequestDto;
import com.dog.feliz.user.service.controller.dto.UserResponseDto;
import com.dog.feliz.user.service.entity.AddressEntity;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.repository.AddressRepository;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import com.dog.feliz.user.service.shared.exception.AddressNotFoundException;
import com.dog.feliz.user.service.shared.exception.ConflictUserException;
import com.dog.feliz.user.service.shared.exception.UserNotFoundException;
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

    private final StringHasher stringHasher;

    public UserService(
            UserRepository userRepository,
            AddressRepository addressRepository,
            BCryptPasswordEncoder passwordEncoder,
            ValidationService validationService,
            StringHasher stringHasher
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.stringHasher = stringHasher;
    }

    public List<UserResponseDto> getUsers() {
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userEntity -> new UserResponseDto(userEntity)).toList();
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id %d".formatted(id)));
    }

    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    public UserResponseDto addUser(UserRequestDto userRequestDto) {
        String mailAddressHash = stringHasher.hash(userRequestDto.getMailAddress());
        if (userRepository.findByMailAddressHash(mailAddressHash).isPresent()) {
            throw new ConflictUserException("Already exists an user with requested email");
        }
        AddressEntity address = addressRepository.save(new AddressEntity(userRequestDto.getAddress()));
        UserEntity user = userRepository.save(
                new UserEntity(
                        userRequestDto,
                        address,
                        passwordEncoder.encode(userRequestDto.getPassword()),
                        mailAddressHash
                )
        );
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        UserEntity userEntity = verifyUserId(id);
        Long addressId = userEntity.getAddress().getId();
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        if (addressEntity.isEmpty()) {
            throw new AddressNotFoundException("Address not found by id %d".formatted(addressId));
        }

        AddressEntity addressUpdated = addressRepository.save(
                new AddressEntity(addressEntity.get().getId(), userRequestDto.getAddress()));
        UserEntity userUpdated = userRepository.save(new UserEntity(id, userRequestDto, addressUpdated));
        return new UserResponseDto(userUpdated);
    }

    public void updateReceiveNotification(Long userId, Boolean receiveNotification) {
        UserEntity userEntity = verifyUserId(userId);
        userEntity.setReceiveNotifications(receiveNotification);
        userRepository.save(userEntity);
    }

    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequest) {
        UserEntity userEntity = userRepository.findByPhone(updatePasswordRequest.phone())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by requested phone and password, verify your credentials"
                ));
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
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException("User not found by id %d".formatted(userId));
        }
        return userEntity.get();
    }
}
