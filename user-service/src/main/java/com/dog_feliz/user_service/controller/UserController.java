package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.AuthorizeRequestDto;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/authorize")
    private ResponseEntity<UserResponseDto> authorize(
        @RequestBody AuthorizeRequestDto authorizeRequestDto
    ){
        return ResponseEntity.ok(userService.authorize(authorizeRequestDto));
    }

    @PostMapping
    private ResponseEntity<UserResponseDto> addUser(
        @RequestBody UserRequestDto userRequest
    ) {
        return ResponseEntity.ok(userService.addUser(userRequest));
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


