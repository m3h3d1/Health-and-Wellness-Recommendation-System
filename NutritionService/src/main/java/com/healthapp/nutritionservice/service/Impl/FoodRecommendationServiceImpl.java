//package com.healthapp.nutritionservice.service.Impl;
//
//import com.healthapp.nutritionservice.dto.FoodDTO;
//import com.healthapp.nutritionservice.dto.FoodRecommendationRequestDTO;
//import com.healthapp.nutritionservice.dto.FoodRecommendationResponseDTO;
//import com.healthapp.nutritionservice.entity.Food;
//import com.healthapp.nutritionservice.repository.FoodRepository;
//import com.healthapp.nutritionservice.service.FoodRecommendationService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FoodRecommendationServiceImpl implements FoodRecommendationService {
//
//    @Autowired
//    private FoodRepository foodRepository;
//
//    @Override
//    public FoodRecommendationResponseDTO getRecommendedFoods(FoodRecommendationRequestDTO requestDTO) {
//        String nutritionalCriterion = requestDTO.getNutritionalCriterion();
//
//        List<Food> recommendedFoods = retrieveRecommendedFoodsBasedOnCriterion(nutritionalCriterion);
//
//        List<FoodDTO> recommendedFoodDTOs = recommendedFoods.stream()
//                .map(this::mapToFoodDTO)
//                .collect(Collectors.toList());
//
//        // Create the response DTO
//        FoodRecommendationResponseDTO responseDTO = new FoodRecommendationResponseDTO();
//        responseDTO.setNutritionalCriterion(nutritionalCriterion);
////        responseDTO.setRecommendedFoods(recommendedFoodDTOs);
//
//        return responseDTO;
//    }
//
//    private List<Food> retrieveRecommendedFoodsBasedOnCriterion(String nutritionalCriterion) {
//        if ("Vitamin-B".equalsIgnoreCase(nutritionalCriterion)) {
//            return foodRepository.findByCategory("Vitamin-B Foods");
//        } else if ("Protein".equalsIgnoreCase(nutritionalCriterion)) {
//            return foodRepository.findByCategory("High-Protein Foods");
//        } else {
//            return Collections.emptyList();
//        }
//    }
//
//    private FoodDTO mapToFoodDTO(Food food) {
//        FoodDTO foodDTO = new FoodDTO();
//        foodDTO.setFoodId(food.getFoodId());
//        foodDTO.setName(food.getName());
//        foodDTO.setCategory(food.getCategory());
//        foodDTO.setDescription(food.getDescription());
//        return foodDTO;
//    }
//}
