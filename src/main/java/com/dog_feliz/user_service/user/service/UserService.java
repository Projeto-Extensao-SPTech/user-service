package com.dog_feliz.user_service.user.service;

import com.dog_feliz.user_service.shared.exception.ConflictUserException;
import com.dog_feliz.user_service.shared.exception.UserNotFoundException;
import com.dog_feliz.user_service.user.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.user.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.shared.converter.crypto.StringCryptoConverter;
import com.dog_feliz.user_service.user.entity.AddressEntity;
import com.dog_feliz.user_service.user.entity.UserEntity;
import com.dog_feliz.user_service.shared.exception.AddressNotFoundException;
import com.dog_feliz.user_service.user.repository.AddressRepository;
import com.dog_feliz.user_service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<UserResponseDto> getUsers(){
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userEntity -> new UserResponseDto(userEntity)).toList();
    }

    public UserResponseDto getUserById(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundException("User not found by id %d".formatted(id));
        return new UserResponseDto(userEntity.get());
    }

    public UserResponseDto addUser(UserRequestDto userRequestDto){
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) throw new ConflictUserException("Already exists an user with requested email");
        AddressEntity address = addressRepository.save(new AddressEntity(userRequestDto.getAddress()));
        UserEntity user = userRepository.save(new UserEntity(userRequestDto, address, passwordEncoder.encode(userRequestDto.getPassword())));
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundException("User not found by id %d".formatted(id));

        Long addressId = userEntity.get().getAddress().getId();
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        if (addressEntity.isEmpty()) throw new AddressNotFoundException("Address not found by id %d".formatted(addressId));

        AddressEntity addressUpdated = addressRepository.save(new AddressEntity(addressEntity.get().getId(), userRequestDto.getAddress()));
        UserEntity userUpdated = userRepository.save(new UserEntity(id, userRequestDto, addressUpdated));
        return new UserResponseDto(userUpdated);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
