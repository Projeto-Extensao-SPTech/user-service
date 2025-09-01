package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.UserRequestDto;
import com.dog_feliz.user_service.controller.dto.UserResponseDto;
import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    private List<UserResponseDto> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    private Optional<UserResponseDto> getUser(
        @PathVariable Integer id
    ){
        return userService.getUserById(id);
    }

    @PostMapping
    private UserResponseDto addUser(
        @RequestBody UserRequestDto userRequest
    ) {
        return userService.addUser(userRequest);
    }

    @PutMapping
    private UserResponseDto updateUser(
        @RequestBody UserRequestDto userRequest
    ) {
        return userService.updateUser(userRequest);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(
        @PathVariable Integer id
    ) {
        userService.deleteUser(id);
    }
}


