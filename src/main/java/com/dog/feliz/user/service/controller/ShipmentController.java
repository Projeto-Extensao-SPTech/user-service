package com.dog.feliz.user.service.controller;

import com.dog.feliz.user.service.controller.dto.ShipmentDetailsResponseDto;
import com.dog.feliz.user.service.controller.dto.ShipmentRequestDto;
import com.dog.feliz.user.service.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/calculate")
    private List<ShipmentDetailsResponseDto> calculateShipment(@RequestBody ShipmentRequestDto shipmentRequestDto) {
        List<ShipmentDetailsResponseDto> result = shipmentService.calculate(shipmentRequestDto);

        log.info("[CALCULATE_SHIPMENT] Shipment calculated successfully totalOptions={}", result.size());
        return result;
    }
}