package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.*;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.service.AuthService;
import com.dog_feliz.user_service.service.JwtService;
import com.dog_feliz.user_service.service.TokenService;
import com.dog_feliz.user_service.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final TokenService tokenService;
    private final JwtService jwtService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, UserService userService, TokenService tokenService, JwtService jwtService) {
        this.authService = authService;
        this.userService = userService;
        this.tokenService = tokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto registeredUser = userService.addUser(userRequestDto);
        log.info("[REGISTER_USER] User registered successfully by request: {}", userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(
            @RequestBody @Valid
            AuthRequestDto authRequestDto
    ) {
        UserEntity authenticatedUser = authService.authenticate(
                authRequestDto.getMailAddress(),
                authRequestDto.getPassword()
        );
        Pair<String, String> tokenObject = tokenService.generateToken(authenticatedUser, null);

        AuthResponseDto authResponseDto = new AuthResponseDto(tokenObject.getFirst(), tokenObject.getSecond(), authenticatedUser);
        log.info("[LOGIN_USER] User authorized to login successfully by request={}", authRequestDto);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/login/refresh")
    public ResponseEntity<AuthResponseDto> refresh(
            @RequestBody @Valid
            RefreshTokenRequestDto refreshTokenRequestDto
    ) {
        UserEntity authenticatedUser = authService.authenticate(
                jwtService.extractClaim(refreshTokenRequestDto.jwtToken(), Claims::getSubject),
                refreshTokenRequestDto.password());
        Pair<String, String> tokenObject = tokenService.generateToken(authenticatedUser, refreshTokenRequestDto.refreshToken());

        AuthResponseDto authResponseDto = new AuthResponseDto(tokenObject.getFirst(), tokenObject.getSecond(), authenticatedUser);
        log.info("[LOGIN_REFRESH_USER] User authorized to login refresh successfully");
        return ResponseEntity.ok(authResponseDto);
    }
}

