package com.healthapp.dataanalysisservice.network;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "USER-APP", configuration = CustomErrorDecoder.class)
public interface UserServiceProxy {
    @GetMapping("/users/profile/read-by-id/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable UUID userId);
}
