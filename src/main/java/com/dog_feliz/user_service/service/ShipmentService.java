package com.dog_feliz.user_service.service;

import com.dog_feliz.user_service.controller.dto.ShipmentRequestDto;
import com.dog_feliz.user_service.controller.dto.ShipmentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ShipmentService {
    @Autowired
    EnvironmentService environmentService;

    RestTemplate template = new RestTemplate();

    public ShipmentResponseDto calculate(ShipmentRequestDto shipmentRequest) {
        String url = environmentService.getProperty("melhor-envio.api.url", String.class) + "/api/v2/me/shipment/calculate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "application/json");
        headers.set("User-Agent", environmentService.getProperty("melhor-envio.api.user-agent", String.class));
        headers.set("Authorization", "Bearer " + environmentService.getProperty("melhor-envio.api.token", String.class));

        HttpEntity<ShipmentRequestDto> request = new HttpEntity<>(shipmentRequest, headers);
        try {
            ResponseEntity<ShipmentResponseDto> response = template.postForEntity(url, request, ShipmentResponseDto.class);
            return response.getBody();
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }
    }

}
