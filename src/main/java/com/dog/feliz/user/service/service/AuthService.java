package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.repository.UserRepository;
import com.dog.feliz.user.service.shared.crypto.hash.StringHasher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final StringHasher stringHasher;

    public AuthService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            StringHasher stringHasher
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.stringHasher = stringHasher;
    }

    public UserEntity authenticate(String mailAddress, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        mailAddress,
                        password
                )
        );

        return userRepository.findByMailAddressHash(stringHasher.hash(mailAddress))
                .orElseThrow();
    }
}
