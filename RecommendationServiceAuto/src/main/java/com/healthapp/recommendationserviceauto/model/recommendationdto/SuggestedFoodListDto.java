package com.healthapp.recommendationserviceauto.model.recommendationdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@RequiredArgsConstructor
public class SuggestedFoodListDto {
        private UUID foodId;
        private String name;
        private String category;
        private String description;
        private double calorie;
        private double protein;
        private double carbohydrates;
        private double fat;
        private double fiber;
        private double vitamins;
        private double minerals;
        private double sugar;
        private double sodium;
        private double cholesterol;
}
