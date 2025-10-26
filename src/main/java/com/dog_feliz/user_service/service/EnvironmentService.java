package com.dog_feliz.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService {
    @Autowired
    private Environment environment;

    public <T> T getProperty(String propertyName, Class<T> targetType) {
        return environment.getProperty(propertyName, targetType);
    }
}
