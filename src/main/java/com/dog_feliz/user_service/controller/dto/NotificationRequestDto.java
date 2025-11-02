package com.dog_feliz.user_service.controller.dto;

public class NotificationRequestDto {

        private String number;
        private String text;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

