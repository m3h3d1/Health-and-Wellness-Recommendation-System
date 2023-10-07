package com.healthapp.communityservice.networks;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "NOTIFICATION-APP", configuration = CustomErrorDecoder.class)
public interface NotificationServiceProxy {
    @PostMapping("/notifications/{userId}")
    public ResponseEntity<String> send(@PathVariable UUID userId, @RequestParam String key, @RequestBody NotificationDTO notification);
}
