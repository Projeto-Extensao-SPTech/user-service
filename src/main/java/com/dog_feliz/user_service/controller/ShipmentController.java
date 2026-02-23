package com.dog_feliz.user_service.controller;

import com.dog_feliz.user_service.controller.dto.ShipmentDetailsResponseDto;
import com.dog_feliz.user_service.controller.dto.ShipmentRequestDto;
import com.dog_feliz.user_service.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping("/calculate_origem_destination")
    public ResponseEntity<ShipmentDetailsResponseDto> calculate(
            @RequestParam String origin,
            @RequestParam String destination
    ) {
        ShipmentRequestDto request = new ShipmentRequestDto();

        ShipmentRequestDto.FromDto originAddress = new ShipmentRequestDto.FromDto();
        originAddress.setPostalCode(origin);
        request.setFrom(originAddress);

        ShipmentRequestDto.ToDto destinationAddress = new ShipmentRequestDto.ToDto();
        destinationAddress.setPostalCode(destination);
        request.setTo(destinationAddress);

        request.setProducts(List.of(createDefaultProduct()));

        List<ShipmentDetailsResponseDto> options = shipmentService.calculate(request);

        ShipmentDetailsResponseDto cheapestOption = findCheapestOption(options);

        if (cheapestOption == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cheapestOption);
    }

    private ShipmentRequestDto.ProductDto createDefaultProduct() {
        ShipmentRequestDto.ProductDto product = new ShipmentRequestDto.ProductDto();
        product.setId("DOACAO-PADRAO");
        product.setQuantity(1);
        product.setWeight(1.0);
        product.setHeight(15);
        product.setWidth(20);
        product.setLength(20);
        product.setInsuranceValue(20.0);
        return product;
    }

    private ShipmentDetailsResponseDto findCheapestOption(List<ShipmentDetailsResponseDto> options) {
        if (options == null || options.isEmpty()) {
            return null;
        }

        return options.stream()
                .min(Comparator.comparingDouble(opt -> {
                    try {
                        return Double.parseDouble(opt.getPrice());
                    } catch (Exception e) {
                        return 99999.0;
                    }
                }))
                .orElse(null);
    }
}