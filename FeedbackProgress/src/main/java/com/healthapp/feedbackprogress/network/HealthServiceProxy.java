package com.healthapp.feedbackprogress.network;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ACCOUNTS-APP", configuration = CustomErrorDecoder.class)
public interface HealthServiceProxy {
    @GetMapping("/api/internal/user/{userId}")
    public ResponseEntity<HealthDTO> getUser(@PathVariable String userId);
}
