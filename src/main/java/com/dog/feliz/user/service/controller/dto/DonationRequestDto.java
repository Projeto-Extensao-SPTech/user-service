package com.dog.feliz.user.service.controller.dto;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DonationRequestDto {

    @NotBlank(message = "O nome do item é obrigatório")
    @Size(max = 40)
    private String name;

    @NotBlank(message = "O tipo é obrigatório")
    private String type;

    @NotNull(message = "A quantidade é obrigatória")
    @Positive
    private Integer amount;

    @NotBlank(message = "O estado do item é obrigatório")
    private String state;

    private String description;

    @NotBlank(message = "O método de envio é obrigatório")
    private String shippingMethod;

    private Integer collectionCenterId;

    private MultipartFile image;

    public DonationRequestDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public Integer getCollectionCenterId() {
        return collectionCenterId;
    }

    public void setCollectionCenterId(Integer collectionCenterId) {
        this.collectionCenterId = collectionCenterId;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}