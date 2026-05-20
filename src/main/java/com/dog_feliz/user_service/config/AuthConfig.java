package com.dog_feliz.user_service.config;

import com.dog_feliz.user_service.repository.UserRepository;
import com.dog_feliz.user_service.shared.crypto.hash.StringHasher;
import com.dog_feliz.user_service.shared.exception.UnauthorizedUserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AuthConfig {

    private final UserRepository userRepository;
    private final StringHasher stringHasher;

    public AuthConfig(UserRepository userRepository, StringHasher stringHasher) {
        this.userRepository = userRepository;
        this.stringHasher = stringHasher;
    }

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByMailAddressHash(stringHasher.hash(username))
                .orElseThrow(() -> new UnauthorizedUserException("Usuário não permitido, verifique suas credenciais"));
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            BCryptPasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
}
