package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.ShipmentDetailsResponseDto;
import com.dog_feliz.user_service.controller.dto.ShipmentRequestDto;
import com.dog_feliz.user_service.service.ShipmentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/calculate")
    private List<ShipmentDetailsResponseDto> calculateShipment(@RequestBody ShipmentRequestDto shipmentRequestDto) {
        return shipmentService.calculate(shipmentRequestDto);
    }
}
