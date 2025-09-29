package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.AddressResponseDto;
import com.dog_feliz.user_service.controller.dto.AuthorizeRequestDto;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.converter.crypto.StringCryptoConverter;
import com.dog_feliz.user_service.entity.AddressEntity;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.exception.AddressNotFoundById;
import com.dog_feliz.user_service.exception.UnauthorizedUser;
import com.dog_feliz.user_service.exception.UserNotFoundByEmail;
import com.dog_feliz.user_service.exception.UserNotFoundById;
import com.dog_feliz.user_service.repository.AddressRepository;
import com.dog_feliz.user_service.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.swing.text.html.Option;
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
        String emailEncrypted = stringCryptoConverter.encrypt(authorizeRequestDto.getEmail());
        Optional<UserEntity> userEntity = userRepository.findByEmail(emailEncrypted);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundByEmail(emailEncrypted);
        }
        if (!userEntity.get().getPassword().equals(authorizeRequestDto.getPassword())) {
            throw new UnauthorizedUser(authorizeRequestDto.getEmail(), authorizeRequestDto.getPassword());
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
