package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.entity.UserEntity;
import com.dog_feliz.user_service.repository.UserRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;

    public UserEntity addUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }
}
