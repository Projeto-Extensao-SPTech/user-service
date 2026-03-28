package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.ShipmentDetailsResponseDto;
import com.dog_feliz.user_service.controller.dto.ShipmentRequestDto;
import com.dog_feliz.user_service.service.ShipmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    private final ShipmentService shipmentService;
    private static final Logger log = LoggerFactory.getLogger(ShipmentController.class);

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/calculate")
    private List<ShipmentDetailsResponseDto> calculateShipment(@RequestBody ShipmentRequestDto shipmentRequestDto) {
        List<ShipmentDetailsResponseDto> result = shipmentService.calculate(shipmentRequestDto);

        log.info("[CALCULATE_SHIPMENT] Shipment calculated successfully totalOptions={}", result.size());
        return result;
    }

    @GetMapping("/calculate_origem_destination")
    public ResponseEntity<ShipmentDetailsResponseDto> calculate(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        ShipmentRequestDto request = new ShipmentRequestDto();
        ShipmentRequestDto.FromDto from = new ShipmentRequestDto.FromDto();
        from.setPostalCode(origin);
        request.setFrom(from);

        ShipmentRequestDto.ToDto to = new ShipmentRequestDto.ToDto();
        to.setPostalCode(destination);
        request.setTo(to);

        ShipmentRequestDto.ProductDto product = new ShipmentRequestDto.ProductDto();
        product.setId("DOACAO-PADRAO");
        product.setQuantity(1);
        product.setWeight(1.0);
        product.setHeight(15);
        product.setWidth(20);
        product.setLength(20);
        product.setInsuranceValue(20.0);

        request.setProducts(List.of(product));

        List<ShipmentDetailsResponseDto> options = shipmentService.calculate(request);

        ShipmentDetailsResponseDto cheapestOption = options.stream()
                .min(Comparator.comparingDouble(opt -> {
                    try {
                        return Double.parseDouble(opt.getPrice());
                    } catch (Exception e) {
                        return 99999.0;
                    }
                }))
                .orElse(null);

        if (cheapestOption == null) {
            log.warn("[CALCULATE_SHIPMENT] No shipment option found origin={} destination={}", origin, destination);
            return ResponseEntity.notFound().build();
        }

        log.info("[CALCULATE_SHIPMENT] Shipment calculated successfully origin={} destination={} price={}",
                origin, destination, cheapestOption.getPrice());
        return ResponseEntity.ok(cheapestOption);
    }
}