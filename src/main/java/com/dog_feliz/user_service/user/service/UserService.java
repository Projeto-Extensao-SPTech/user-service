package com.dog_feliz.user_service.user.service;

import com.dog_feliz.user_service.user.controller.dto.AuthorizeRequestDto;
import com.dog_feliz.user_service.user.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.user.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.shared.converter.crypto.StringCryptoConverter;
import com.dog_feliz.user_service.user.entity.AddressEntity;
import com.dog_feliz.user_service.user.entity.UserEntity;
import com.dog_feliz.user_service.shared.exception.AddressNotFoundById;
import com.dog_feliz.user_service.shared.exception.UnauthorizedUser;
import com.dog_feliz.user_service.shared.exception.UserNotFoundByEmail;
import com.dog_feliz.user_service.shared.exception.UserNotFoundById;
import com.dog_feliz.user_service.user.repository.AddressRepository;
import com.dog_feliz.user_service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private StringCryptoConverter stringCryptoConverter;

    public List<UserResponseDto> getUsers(){
        List<UserEntity> users = userRepository.findAll();
        return users.stream().map(userEntity -> new UserResponseDto(userEntity)).toList();
    }

    public UserResponseDto getUserById(Long id){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundById(id);
        return new UserResponseDto(userEntity.get());
    }

    public UserResponseDto authorize(AuthorizeRequestDto authorizeRequestDto){
        String email = authorizeRequestDto.getEmail();
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundByEmail(email);
        }
        if (!userEntity.get().getPassword().equals(authorizeRequestDto.getPassword())) {
            throw new UnauthorizedUser(email, authorizeRequestDto.getPassword());
        }
        return new UserResponseDto(userEntity.get());
    }

    public UserResponseDto addUser(UserRequestDto userRequestDto){
        AddressEntity address = addressRepository.save(new AddressEntity(userRequestDto.getAddress()));
        UserEntity user = userRepository.save(new UserEntity(userRequestDto, address));
        return new UserResponseDto(user);
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto){
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) throw new UserNotFoundById(id);

        Long addressId = userEntity.get().getAddress().getId();
        Optional<AddressEntity> addressEntity = addressRepository.findById(addressId);
        if (addressEntity.isEmpty()) throw new AddressNotFoundById(addressId);

        AddressEntity addressUpdated = addressRepository.save(new AddressEntity(addressEntity.get().getId(), userRequestDto.getAddress()));
        UserEntity userUpdated = userRepository.save(new UserEntity(id, userRequestDto, addressUpdated));
        return new UserResponseDto(userUpdated);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
