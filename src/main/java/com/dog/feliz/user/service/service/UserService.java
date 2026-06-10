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
import com.dog.feliz.user.service.shared.exception.InvalidRecoveryCodeException;
import com.dog.feliz.user.service.shared.exception.UserNotFoundException;
import com.dog.feliz.user.service.controller.dto.NotificationSendRequest;
import com.dog.feliz.user.service.controller.dto.NotificationType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    private final StringRedisTemplate recoveryCodeRedisTemplate;

    private static final String RECOVERY_PREFIX = "recovery:";

    private static final long RECOVERY_TTL_MINUTES = 15L;

    public UserService(
            UserRepository userRepository,
            AddressRepository addressRepository,
            BCryptPasswordEncoder passwordEncoder,
            ValidationService validationService,
            NotificationService notificationService,
            StringHasher stringHasher,
            StringRedisTemplate recoveryCodeRedisTemplate
    ) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
        this.validationService = validationService;
        this.notificationService = notificationService;
        this.stringHasher = stringHasher;
        this.recoveryCodeRedisTemplate = recoveryCodeRedisTemplate;
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
        String mailHash = stringHasher.hash(mail);
        var findUserByEmail = userRepository.findByMailAddressHash(mailHash);

        return findUserByEmail.isPresent();
    }

    public void sendCodeForMail(String mail) {
        String mailHash = stringHasher.hash(mail);

        UserEntity user = userRepository.findByMailAddressHash(mailHash)
                .orElseThrow(() -> new UserNotFoundException("User not found by mail"));

        String code = generateRandomCode();

        recoveryCodeRedisTemplate.opsForValue().set(
                RECOVERY_PREFIX + mailHash,
                code,
                Duration.ofMinutes(RECOVERY_TTL_MINUTES)
        );

        notificationService.send(new NotificationSendRequest(
                NotificationType.UPDATE_PASSWORD,
                mail,
                code,
                user.getId()
        ));
    }

    public void validateCode(String mail, String code) {
        String mailHash = stringHasher.hash(mail);
        String stored = recoveryCodeRedisTemplate.opsForValue()
                .get(RECOVERY_PREFIX + mailHash);

        if (stored == null || !stored.equals(code)) {
            throw new InvalidRecoveryCodeException("Invalid or expired recovery code");
        }

        recoveryCodeRedisTemplate.delete(RECOVERY_PREFIX + mailHash);
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
                new AddressEntity(
                        addressEntity.get().getId(),
                        userRequestDto.getAddress()
                )
        );

        UserEntity userUpdated = userRepository.save(
                new UserEntity(id, userRequestDto, addressUpdated)
        );
        return new UserResponseDto(userUpdated);
    }

    public void updateReceiveNotification(Long userId, Boolean receiveNotification) {
        UserEntity userEntity = verifyUserId(userId);
        userEntity.setReceiveNotifications(receiveNotification);
        userRepository.save(userEntity);
    }

    public void updatePassword(UpdatePasswordRequestDto updatePasswordRequest) {
        String mailAddressHash = stringHasher.hash(updatePasswordRequest.mail());

        UserEntity userEntity = userRepository.findByMailAddressHash(mailAddressHash)
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found by requested mail, verify your credentials"
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

    private String generateRandomCode() {
        return Integer.toString(1000 + (int) (Math.random() * 9000));
    }
}
