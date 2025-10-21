package com.dog_feliz.user_service.auth.service;

import com.dog_feliz.user_service.auth.controller.dto.AuthRequestDto;
import com.dog_feliz.user_service.user.entity.UserEntity;
import com.dog_feliz.user_service.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserEntity authenticate(AuthRequestDto authRequestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDto.getEmail(),
                        authRequestDto.getPassword()
                )
        );

        return userRepository.findByEmail(authRequestDto.getEmail())
                .orElseThrow();
    }
}
