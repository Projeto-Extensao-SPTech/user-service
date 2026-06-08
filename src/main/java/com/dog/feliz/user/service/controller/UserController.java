package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.controller.dto.UpdatePasswordRequestDto;
import com.dog.feliz.user.service.controller.dto.UserRequestDto;
import com.dog.feliz.user.service.controller.dto.UserResponseDto;
import com.dog.feliz.user.service.entity.user.UserEntity;
import com.dog.feliz.user.service.service.UserService;
import com.dog.feliz.user.service.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final ValidationService validationService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @GetMapping
    private ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        log.info("[GET_USERS] Users fetched successfully total={}", users.size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponseDto> getUser(
            @PathVariable Long id
    ) {
        validationService.verifyIsValidUserId(id);
        UserResponseDto user = toResponse(userService.getUserById(id));

        log.info("[GET_USER_BY_ID] User fetched successfully userId={}", id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    private ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto userRequest
    ) {
        validationService.verifyIsValidUserId(id);
        UserResponseDto updatedUser = userService.updateUser(id, userRequest);

        log.info("[UPDATE_USER] User updated successfully userId={}", id);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping("/notification/{id}/{receiveNotification}")
    private void updateReceiveNotification(
            @PathVariable Long id,
            @PathVariable Boolean receiveNotification
    ) {
        validationService.verifyIsValidUserId(id);
        userService.updateReceiveNotification(id, receiveNotification);
        log.info("[UPDATE_USER_NOTIFICATION] Notification preference updated userId={} receiveNotification={}",
                id, receiveNotification);
    }

    @GetMapping("/exists-by-phone/{phone}")
    private Boolean existsByPhone(
            @PathVariable String phone
    ) {
        Boolean exists = userService.existsByPhone(phone);
        log.info("[EXISTS_USER_BY_PHONE] Check executed phoneExists={}", exists);
        return exists;
    }

    @PatchMapping("/update-password")
    private void updatePassword(
            @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto
    ) {
        userService.updatePassword(updatePasswordRequestDto);
        log.info("[UPDATE_USER_PASSWORD] Password updated successfully");
    }

    @DeleteMapping("/{id}")
    private void deleteUser(
            @PathVariable Long id
    ) {
        validationService.verifyIsValidUserId(id);
        userService.deleteUser(id);
        log.info("[DELETE_USER] User deleted successfully userId={}", id);
    }

    private UserResponseDto toResponse(UserEntity user) {
        return new UserResponseDto(user);
    }
}