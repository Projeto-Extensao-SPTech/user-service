package com.dog.feliz.user.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ShipmentRequestDto {
    private FromDto from;

    private ToDto to;

    private List<ProductDto> products;

    private OptionsDto options;

    private String services;

    public static class FromDto {
        @JsonProperty("postal_code")

        private String postalCode;

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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class OptionsDto {
        private boolean receipt;

        @JsonProperty("own_hand")
        private boolean ownHand;
    }

    public ToDto getTo() {
        return to;
    }

    public void setTo(ToDto to) {
        this.to = to;
    }
}
