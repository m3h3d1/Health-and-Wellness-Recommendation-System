package com.healthapp.recommendationservicemanual.networks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "RECOMMENDATION-APP", configuration = CustomErrorDecoder.class)
public interface RecommendationAutoProxy {
    @GetMapping("/health/diet/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsDietRec(@PathVariable UUID recommendId);

    @GetMapping("/health/exercise/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsExerciseRec(@PathVariable UUID recommendId);

    @GetMapping("/health/sleep/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsSleepRec(@PathVariable UUID recommendId);
}

