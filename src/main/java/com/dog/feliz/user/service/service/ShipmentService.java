package com.dog.feliz.user.service.service;

import com.dog.feliz.user.service.controller.dto.ShipmentDetailsResponseDto;
import com.dog.feliz.user.service.controller.dto.ShipmentRequestDto;
import com.dog.feliz.user.service.controller.dto.ShipmentResponseDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ShipmentService {

    private final EnvironmentService environmentService;

    private final RestTemplate template;

    public ShipmentService(EnvironmentService environmentService, RestTemplate template) {
        this.environmentService = environmentService;
        this.template = template;
    }

    public List<ShipmentDetailsResponseDto> calculate(ShipmentRequestDto shipmentRequest) {
        String url = environmentService.getProperty(
                "melhor-envio.api.url", String.class
        ) + "/api/v2/me/shipment/calculate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", environmentService.getProperty(
                "melhor-envio.api.user-agent", String.class
        ));
        headers.set("Authorization", "Bearer "
                + environmentService.getProperty(
                        "melhor-envio.api.token", String.class)
        );

        HttpEntity<ShipmentRequestDto> request = new HttpEntity<>(shipmentRequest, headers);
        try {
            ResponseEntity<List<ShipmentResponseDto>> response = template.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<>() {}
            );
            List<ShipmentResponseDto> body = response.getBody();
            return body.stream()
                    .map(shipment -> new ShipmentDetailsResponseDto(shipment))
                    .toList();
        } catch (HttpClientErrorException e) {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "Melhor Envio client error: " + e.getMessage());
        } catch (RestClientException e) {
            throw new HttpServerErrorException(HttpStatus.BAD_GATEWAY, "RestTemplate error: " + e.getMessage());
        }
    }
}
