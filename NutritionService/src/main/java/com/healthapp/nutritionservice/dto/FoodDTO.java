package com.healthapp.nutritionservice.dto;

import lombok.Setter;

import java.util.UUID;

@Setter
public class FoodDTO {
    private UUID foodId;
    private String name;
    private String category;
    private String description;
}
