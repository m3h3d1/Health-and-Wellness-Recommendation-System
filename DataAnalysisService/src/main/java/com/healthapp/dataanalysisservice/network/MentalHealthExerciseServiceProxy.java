package com.healthapp.dataanalysisservice.network;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "MENTALHEALTH-APP", configuration = CustomErrorDecoder.class)
public interface MentalHealthExerciseServiceProxy {
    @GetMapping("http://localhost:8700/mental-health/exercises/user/{userId}")
    public ResponseEntity<Object> getMentalHealthExercise(@PathVariable UUID userId);
}
