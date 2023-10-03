package com.healthapp.nutritionservice.repository;

import com.healthapp.nutritionservice.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
//    List<Food> findByFoodIdAndOrganic(UUID foodId, boolean organic);
    Food findByNameContainingIgnoreCase(String name);
}