package com.healthapp.recommendationserviceauto.networkmanager;

import com.healthapp.recommendationserviceauto.model.recommendationdto.SuggestedFoodListDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="nutrition-app")
public interface FoodFeignClient {
    @GetMapping("/nutrition/recommendation/{category}")
    ResponseEntity<List<SuggestedFoodListDto>> getFoodByCategory(@PathVariable String category);
}
