package com.healthapp.userservice.networkmanager;

import com.healthapp.userservice.model.Requestdto.HealthRequestDto;
import com.healthapp.userservice.model.updatedeletedto.ProfileUpdateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name="recommendation-app",configuration = FeignClientConfiguration.class)
public interface HealthFeignClient {
    @PostMapping("/health/add/{userId}")
    ResponseEntity<String> addHealthInfo(@PathVariable UUID userId, @RequestBody HealthRequestDto healthRequestDto);
    @PutMapping("/health/update/{userId}")
    ResponseEntity<String> updateHealthInfo(
            @PathVariable UUID userId,
            @RequestBody ProfileUpdateDto profileUpdateDto
    );
}
