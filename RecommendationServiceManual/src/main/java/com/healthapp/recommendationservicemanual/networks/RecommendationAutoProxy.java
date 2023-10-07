package com.healthapp.recommendationservicemanual.networks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

// RecommendationAutoProxy is a Feign client interface for making remote calls to the RECOMMENDATION-APP service.
@FeignClient(name = "RECOMMENDATION-APP", configuration = CustomErrorDecoder.class)
public interface RecommendationAutoProxy {
    // Checks if a Diet recommendation exists by its recommendation ID.
    @GetMapping("/health/diet/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsDietRec(@PathVariable UUID recommendId);

    // Checks if an Exercise recommendation exists by its recommendation ID.
    @GetMapping("/health/exercise/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsExerciseRec(@PathVariable UUID recommendId);

    // Checks if a Sleep recommendation exists by its recommendation ID.
    @GetMapping("/health/sleep/recommend/if-exists/{recommendId}")
    public ResponseEntity<Boolean> ifExistsSleepRec(@PathVariable UUID recommendId);
}
