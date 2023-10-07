package com.healthapp.userservice.domain;

public enum BloodGroupEnum {
    A_POSITIVE("A+"),
    A_NEGATIVE("A-"),
    B_POSITIVE("B+"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-");

    private final String value;

    BloodGroupEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
    public static BloodGroupEnum fromString(String text) {
        for (BloodGroupEnum bg : BloodGroupEnum.values()) {
            if (bg.value.equalsIgnoreCase(text)) {
                return bg;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
