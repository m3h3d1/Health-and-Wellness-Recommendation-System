package com.healthapp.nutritionservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "recipe_id")
    private UUID recipeId;

    @Column(name = "cooking_process", columnDefinition = "TEXT")
    private String cookingProcess;

    @Column(name = "cooking_time_minutes")
    private int cookingTimeMinutes;


    @ManyToMany
    @JsonIgnoreProperties("recipes")
    @JoinTable(
            name = "recipe_food",  // Name of the join table
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "food_id")
    )
    private List<Food> foods = new ArrayList<>();
}
