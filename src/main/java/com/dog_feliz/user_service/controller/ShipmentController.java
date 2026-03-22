package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.ShipmentDetailsResponseDto;
import com.dog_feliz.user_service.controller.dto.ShipmentRequestDto;
import com.dog_feliz.user_service.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
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
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cheapestOption);
    }


}
