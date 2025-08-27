package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    private List<UserEntity> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    private Optional<UserEntity> getUser(
        @PathVariable Integer id
    ){
        return userService.getUserById(id);
    }

    @PostMapping
    private UserEntity addUser(
        @RequestBody UserEntity userEntity
    ) {
        return userService.addUser(userEntity);
    }

    @PutMapping
    private UserEntity updateUser(
        @RequestBody UserEntity userEntity
    ) {
        return userService.updateUser(userEntity);
    }

    @DeleteMapping("/{id}")
    private void deleteUser(
        @PathVariable Integer id
    ) {
        userService.deleteUser(id);
    }
}


