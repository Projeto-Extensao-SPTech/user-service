package com.dog.feliz.user.service.controller.dto;

public class ShipmentDetailsResponseDto {
    private Integer id;

    private String name;

    private String price;

    private Integer deliveryTime;

    private DeliveryRangeDto deliveryRange;

    private String companyName;

    private String companyImage;

    public ShipmentDetailsResponseDto() {
    }

    public ShipmentDetailsResponseDto(ShipmentResponseDto response) {
        this.id = response.getId();
        this.name = response.getName();
        this.price = response.getPrice();
        this.deliveryTime = response.getDeliveryTime();

        if (response.getDeliveryRange() != null) {
            this.deliveryRange = new DeliveryRangeDto(
                    response.getDeliveryRange().getMin(),
                    response.getDeliveryRange().getMax()
            );
        }

        // company
        if (response.getCompany() != null) {
            this.companyName = response.getCompany().getName();
            this.companyImage = response.getCompany().getPicture();
        }
    }

    public static class DeliveryRangeDto {
        private final Integer min;

        private final Integer max;

        public DeliveryRangeDto(Integer min, Integer max) {
            this.min = min;
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public Integer getMax() {
            return max;
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public Integer getDeliveryTime() {
        return deliveryTime;
    }

    public DeliveryRangeDto getDeliveryRange() {
        return deliveryRange;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyImage() {
        return companyImage;
    }
}
