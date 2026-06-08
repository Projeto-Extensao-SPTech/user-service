package com.dog.feliz.user.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class HttpRequestConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
