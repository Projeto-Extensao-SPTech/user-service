package com.dog_feliz.user_service.controller.dto.notification;

public enum AvailableRecurrence {
    THREE_DAYS(3),
    TEN_DAYS(10),
    TWO_WEEKS(14);

    private int value;

    AvailableRecurrence(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
