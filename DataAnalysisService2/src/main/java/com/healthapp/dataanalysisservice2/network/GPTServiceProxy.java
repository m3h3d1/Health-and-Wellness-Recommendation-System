package com.healthapp.dataanalysisservice2.network;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "GPT-APP", configuration = CustomErrorDecoder.class)
public interface GPTServiceProxy {
    @GetMapping("/bot/chat")
    String getData(@RequestParam("prompt") String prompt);
}
