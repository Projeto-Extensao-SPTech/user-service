package com.dog.feliz.user.service.service;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvironmentService {

    private final Environment environment;

    public EnvironmentService(Environment environment) {
        this.environment = environment;
    }

    public <T> T getProperty(String propertyName, Class<T> targetType) {
        return environment.getProperty(propertyName, targetType);
    }
}
