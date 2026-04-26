package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.UpdatePasswordRequestDto;
import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.service.UserService;
import com.dog_feliz.user_service.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private ResponseEntity<Page<UserResponseDto>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        Page<UserResponseDto> users = userService.getUsers(page, size, sortBy);
        log.info("[GET_USERS] Users fetched successfully total={}", users.getContent().size());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    private ResponseEntity<UserResponseDto> getUser(
            @PathVariable Long id
    ){
        validationService.verifyIsValidUserId(id);
        UserResponseDto user = userService.getUserById(id);

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
}