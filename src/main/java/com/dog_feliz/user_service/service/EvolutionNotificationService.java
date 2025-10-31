package com.dog_feliz.user_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class EvolutionNotificationService {

    @Value("${evolution.api.url}")
    private static String evolutionApiUrl;

    @Value("${evolution.api.key}")
    private static String apiInstanceKey;

    @Value("${evolution.instance.name}")
    private static String instanceName;

    RestTemplate template = new RestTemplate();

    public String sendMensage(String instanceName, String number, String message){

        String url = evolutionApiUrl  + "message/sendText" + instanceName;

        HashMap <String,Object> postResponse = new HashMap<>();

        postResponse.put("number", number);
        postResponse.put("text",message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiInstanceKey);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(postResponse, headers);

        ResponseEntity<String> response = template.postForEntity(url, request, String.class);

        return response.getBody();
        
    }

}
