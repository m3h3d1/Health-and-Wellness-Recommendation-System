package com.healthapp.recommendationservicemanual.networks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "MENTAL-HEALTH-APP", configuration = CustomErrorDecoder.class)
public interface RecommendationAutoMentalProxy {
    @GetMapping("recommendation/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsMentalHealthRec(@PathVariable UUID recommendId);
}