package com.healthapp.dataanalysisservice.network;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name = "GPT-APP", configuration = CustomErrorDecoder.class)
public interface GPTServiceProxy {
@GetMapping("http://localhost:8010/bot/chat?prompt={prompt}")
ResponseEntity<Object> getData(@RequestParam("prompt") String prompt);

}
