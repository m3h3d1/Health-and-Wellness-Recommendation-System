package com.healthapp.dataanalysisservice2.network;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "MENTALHEALTH-APP", configuration = CustomErrorDecoder.class)
public interface MoodTrackingServiceProxy {
    @GetMapping("/mental-health/mood-tracking/user/{userId}")
    public ResponseEntity<Object> getMoodTracking(@PathVariable UUID userId);
}
