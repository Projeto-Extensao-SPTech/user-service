package com.dog_feliz.user_service.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DonationRequestDto {

    @NotBlank(message = "O nome do item é obrigatório")
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres")
    private final String name;

    @NotBlank(message = "O tipo é obrigatório")
    @Size(max = 15, message = "O tipo deve ter no máximo 15 caracteres")
    private final String type;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser maior que zero")
    private final Integer amount;

    @NotBlank(message = "O estado do item é obrigatório")
    private final String state; // Vai receber "Novo" ou "Usado"

    @Size(max = 65535, message = "A descrição é muito longa")
    private final String description;

    @NotBlank(message = "O método de envio é obrigatório")
    @Size(max = 20, message = "O método de envio deve ter no máximo 20 caracteres")
    private final String shippingMethod;

    private final Integer collectionCenterId;

    public DonationRequestDto(String name, String type, Integer amount, String state, String description, String shippingMethod, Integer collectionCenterId) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.state = state; // Ajustado
        this.description = description;
        this.shippingMethod = shippingMethod;
        this.collectionCenterId = collectionCenterId;
    }

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public Integer getAmount() { return amount; }

    // Getter Ajustado
    public String getState() {
        return state;
    }

    public String getDescription() { return description; }
    public String getShippingMethod() { return shippingMethod; }
    public Integer getCollectionCenterId() { return collectionCenterId; }
}