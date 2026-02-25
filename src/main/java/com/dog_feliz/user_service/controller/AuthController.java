package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.AuthRequestDto;
import com.dog_feliz.user_service.controller.dto.AuthResponseDto;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.entity.user.UserEntity;
import com.dog_feliz.user_service.service.AuthService;
import com.dog_feliz.user_service.service.JwtService;
import com.dog_feliz.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final JwtService jwtService;
    private final AuthService authService;
    private final UserService userService;

    public AuthController(JwtService jwtService, AuthService authService, UserService userService) {
        this.jwtService = jwtService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto registeredUser = userService.addUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody @Valid AuthRequestDto authRequestDto) {
        UserEntity authenticatedUser = authService.authenticate(authRequestDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, jwtService.getExpirationTime(), authenticatedUser);
        return ResponseEntity.ok(authResponseDto);
    }
}

