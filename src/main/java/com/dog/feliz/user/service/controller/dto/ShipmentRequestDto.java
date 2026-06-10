package com.dog.feliz.user.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShipmentRequestDto {
    private FromDto from;

    private ToDto to;

    private List<ProductDto> products;

    private OptionsDto options;

    private String services;

    public ShipmentRequestDto() {
    }

    public ShipmentRequestDto(FromDto from, ToDto to, List<ProductDto> products, OptionsDto options, String services) {
        this.from = from;
        this.to = to;
        this.products = products;
        this.options = options;
        this.services = services;
    }

    public static class FromDto {
        @JsonProperty("postal_code")
        private String postalCode;

        public FromDto(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public static class ToDto {
        @JsonProperty("postal_code")
        private String postalCode;

        public ToDto(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }
    }

    public static class ProductDto {
        private String id;

        private double width;

        private double height;

        private double length;

        private double weight;

        private double insuranceValue;

        private int quantity;

        public ProductDto(
            String id,
            double width,
            double height,
            double length,
            double weight,
            double insuranceValue,
            int quantity
        ) {
            this.id = id;
            this.width = width;
            this.height = height;
            this.length = length;
            this.weight = weight;
            this.insuranceValue = insuranceValue;
            this.quantity = quantity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }

        public double getInsuranceValue() {
            return insuranceValue;
        }

        public void setInsuranceValue(double insuranceValue) {
            this.insuranceValue = insuranceValue;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class OptionsDto {
        private boolean receipt;

        @JsonProperty("own_hand")
        private boolean ownHand;

        public boolean isReceipt() {
            return receipt;
        }

        public OptionsDto(boolean receipt, boolean ownHand) {
            this.receipt = receipt;
            this.ownHand = ownHand;
        }

        public void setReceipt(boolean receipt) {
            this.receipt = receipt;
        }

        public boolean isOwnHand() {
            return ownHand;
        }

        public void setOwnHand(boolean ownHand) {
            this.ownHand = ownHand;
        }
    }

    public FromDto getFrom() {
        return from;
    }

    public void setFrom(FromDto from) {
        this.from = from;
    }

    public ToDto getTo() {
        return to;
    }

    public void setTo(ToDto to) {
        this.to = to;
    }

    public List<ProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDto> products) {
        this.products = products;
    }

    public OptionsDto getOptions() {
        return options;
    }

    public void setOptions(OptionsDto options) {
        this.options = options;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
