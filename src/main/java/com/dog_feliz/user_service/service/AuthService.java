package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.AuthRequestDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public UserEntity authenticate(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getMailAddress(),
                        authRequestDto.getPassword()
                )
        );

        return userRepository.findByMailAddress(authRequestDto.getMailAddress())
                .orElseThrow();
    }
}
