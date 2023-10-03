package com.healthapp.nutritionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FoodDetailsDTO {
    private UUID foodId;
    private String name;
    private String category;
    private String description;
    private boolean organic;
}
