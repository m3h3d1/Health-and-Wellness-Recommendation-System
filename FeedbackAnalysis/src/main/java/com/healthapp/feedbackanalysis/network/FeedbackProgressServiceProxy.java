package com.healthapp.feedbackanalysis.network;

import com.healthapp.feedbackanalysis.dto.FeedbackDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "FEEDBACKPROGRESS-APP", configuration = CustomErrorDecoder.class)
public interface FeedbackProgressServiceProxy {
    @GetMapping("/feedback/get-all")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback();
}
