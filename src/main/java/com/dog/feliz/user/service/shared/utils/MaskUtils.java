package com.dog.feliz.user.service.shared.utils;

public class MaskUtils {
    public static String maskDocument(String document) {
        if (document == null || document.length() < 2) {
            return "***";
        }
        return "***.***.***-" + document.substring(document.length() - 2);
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) {
            return "*****";
        }
        return "*****-" + phone.substring(phone.length() - 4);
    }

    public static String maskMailAddress(String email) {
        if (email == null || !email.contains("@")) {
            return "***";
        }

        String[] parts = email.split("@");
        String namePart = parts[0];
        String domainPart = parts[1];

        if (namePart.length() <= 1) {
            return "*@" + domainPart;
        }

        return namePart.charAt(0) + "***@" + domainPart;
    }

    public static String maskNameField(String name) {
        if (name == null || name.isEmpty()) {
            return "***";
        }

        StringBuilder result = new StringBuilder(name.length());
        boolean startOfWord = true;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);

            if (Character.isWhitespace(c)) {
                result.append(c);
                startOfWord = true;
            } else {
                if (startOfWord) {
                    result.append(c);
                    startOfWord = false;
                } else {
                    result.append('*');
                }
            }
        }

        return result.toString();
    }
}
