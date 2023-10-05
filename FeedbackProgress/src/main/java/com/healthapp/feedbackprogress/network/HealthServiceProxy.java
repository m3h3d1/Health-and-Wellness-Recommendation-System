package com.healthapp.feedbackprogress.network;

import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "recommendation-app", configuration = CustomErrorDecoder.class)
public interface HealthServiceProxy {
    @GetMapping("/health/{userId}")
    public ResponseEntity<HealthDTO> getHealth(@PathVariable UUID userId);
}
