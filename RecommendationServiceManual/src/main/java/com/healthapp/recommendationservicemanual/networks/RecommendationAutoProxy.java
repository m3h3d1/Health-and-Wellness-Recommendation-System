package com.healthapp.recommendationservicemanual.networks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "RECOMMENDATION-APP", configuration = CustomErrorDecoder.class)
public interface RecommendationAutoProxy {
    @GetMapping("/users/read-by-id/{recommendationId}")
    public ResponseEntity<Boolean> ifExistsDietRec(@PathVariable UUID recommendationId);
}
