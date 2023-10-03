package com.healthapp.nutritionservice.repository;

import com.healthapp.nutritionservice.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, UUID> {
}