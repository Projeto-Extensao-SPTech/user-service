package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.AuthRequestDto;
import com.dog_feliz.user_service.controller.dto.AuthResponseDto;
import com.dog_feliz.user_service.service.AuthService;
import com.dog_feliz.user_service.service.JwtService;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto registeredUser = userService.addUser(userRequestDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> authenticate(@RequestBody AuthRequestDto authRequestDto) {
        UserEntity authenticatedUser = authService.authenticate(authRequestDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, jwtService.getExpirationTime());

        return ResponseEntity.ok(authResponseDto);
    }
}

