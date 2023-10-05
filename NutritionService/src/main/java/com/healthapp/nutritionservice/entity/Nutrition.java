package com.healthapp.nutritionservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "nutrition")
public class Nutrition {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "nutrition_id")
    private UUID nutritionId;

    @Column(name = "calorie")
    private double calorie;

    @Column(name = "protein")
    private double protein;

    @Column(name = "carbohydrates")
    private double carbohydrates;

    @Column(name = "fat")
    private double fat;

    @Column(name = "fiber")
    private double fiber;

    @Column(name = "vitamins")
    private double vitamins;

    @Column(name = "minerals")
    private double minerals;

    @Column(name = "sugar")
    private double sugar;

    @Column(name = "sodium")
    private double sodium;

    @Column(name = "cholesterol")
    private double cholesterol;

    @Column(name = "serving_size")
    private String servingSize;

    @Column(name = "allergenic")
    private boolean allergenic;

    @Column(name = "vegetarian")
    private boolean vegetarian;

    @OneToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @OneToOne
    @JoinColumn(name = "food_id")
    private Food food;
}