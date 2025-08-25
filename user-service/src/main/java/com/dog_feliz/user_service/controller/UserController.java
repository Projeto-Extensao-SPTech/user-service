package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;

    @PostMapping
    private UserEntity addUser(
            @RequestBody
            UserEntity userEntity
    ) {
        return userService.addUser(userEntity);
    }

    @GetMapping
    private List<UserEntity> getUsers(){
        return userService.getUsers();
    }
}


