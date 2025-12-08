package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.UpdatePasswordRequestDto;
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

    @PutMapping("/{id}")
    private ResponseEntity<UserResponseDto> updateUser(
        @PathVariable Long id,
        @RequestBody UserRequestDto userRequest
    ) {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @PatchMapping("/notification/{id}/{receiveNotification}")
    private void updateReceiveNotification(
        @PathVariable Long id,
        @PathVariable Boolean receiveNotification
    ) {
        userService.updateReceiveNotification(id, receiveNotification);
    }

    @GetMapping("/exists-by-phone/{phone}")
    private Boolean existsByPhone(
        @PathVariable String phone
    ) {
        return userService.existsByPhone(phone);
    }

    @PatchMapping("/update-password")
    private void updatePassword(
        @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto
    ) {
        userService.updatePassword(updatePasswordRequestDto);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(
        @PathVariable Long id
    ) {
        userService.deleteUser(id);
    }
}


