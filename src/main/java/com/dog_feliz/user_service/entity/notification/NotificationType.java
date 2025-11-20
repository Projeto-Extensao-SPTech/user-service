package com.dog_feliz.user_service.entity.notification;

public enum NotificationType {
    ADOPTION_FAIR("Feira de adoção se aproximando"),
    DONATION("Precisando de doações"),
    GENERAL("Abrigo Dog Feliz quer falar com você"),
    VOLUNTEER("Precisamos de voluntários");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
