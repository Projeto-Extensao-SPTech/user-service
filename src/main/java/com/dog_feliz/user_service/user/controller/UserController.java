package com.dog_feliz.user_service.user.controller;

import com.dog_feliz.user_service.auth.controller.dto.AuthResponseDto;
import com.dog_feliz.user_service.auth.service.JwtService;
import com.dog_feliz.user_service.user.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.user.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @GetMapping
    private ResponseEntity<List<UserResponseDto>> getUsers(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponseDto> getUser(
        @PathVariable Long id
    ){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserResponseDto> updateUser(
        @PathVariable Long id,
        @RequestBody UserRequestDto userRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("/{id}")
    private void deleteUser(
        @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}


