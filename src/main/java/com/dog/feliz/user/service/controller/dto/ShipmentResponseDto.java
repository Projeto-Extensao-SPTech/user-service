package com.dog.feliz.user.service.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShipmentResponseDto {
    private int id;

    private String name;

    private String price;

    @JsonProperty("custom_price")
    private String customPrice;

    private String discount;

    private String currency;

    @JsonProperty("delivery_time")
    private int deliveryTime;

    @JsonProperty("delivery_range")
    private DeliveryRangeDto deliveryRange;

    @JsonProperty("custom_delivery_time")
    private int customDeliveryTime;

    @JsonProperty("custom_delivery_range")
    private DeliveryRangeDto customDeliveryRange;

    private List<PackageDto> packages;

    @JsonProperty("additional_services")
    private AdditionalServicesDto additionalServices;

    private CompanyDto company;

    public static class DeliveryRangeDto {
        private int min;

        private int max;

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }
    }

    public static class PackageDto {
        private String price;

        private String discount;

        private String format;

        private DimensionsDto dimensions;

        private String weight;

        @JsonProperty("insurance_value")
        private String insuranceValue;

        private List<ProductDto> products;
    }

    public static class DimensionsDto {
        private int height;

        private int width;

        private int length;
    }

    public static class ProductDto {
        private String id;

        private int quantity;
    }

    public static class AdditionalServicesDto {
        private boolean receipt;

        private boolean ownHand;

        private boolean collect;
    }

    public static class CompanyDto {
        private int id;

        private String name;

        private String picture;

        public String getName() {
            return name;
        }

        public String getPicture() {
            return picture;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCustomPrice() {
        return customPrice;
    }

    public String getDiscount() {
        return discount;
    }

    public String getCurrency() {
        return currency;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public DeliveryRangeDto getDeliveryRange() {
        return deliveryRange;
    }

    public int getCustomDeliveryTime() {
        return customDeliveryTime;
    }

    public DeliveryRangeDto getCustomDeliveryRange() {
        return customDeliveryRange;
    }

    public List<PackageDto> getPackages() {
        return packages;
    }

    public AdditionalServicesDto getAdditionalServices() {
        return additionalServices;
    }

    public CompanyDto getCompany() {
        return company;
    }
}
