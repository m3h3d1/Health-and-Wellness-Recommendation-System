package com.healthapp.feedbackprogress.controller;

import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;
import com.healthapp.feedbackprogress.entity.Feedback;
import com.healthapp.feedbackprogress.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/submit")
    public ResponseEntity<String> addFeedback(@RequestBody Feedback feedback) {
        feedbackService.addFeedback(feedback);
        return new ResponseEntity<>("Feedback Data Submitted Successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        try {
            List<Feedback> feedbackList = feedbackService.getAllFeedbacks();
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{foodId}")
    public ResponseEntity<String> updateFeedback(
            @PathVariable UUID feedbackId,
            @RequestBody Feedback updatedFeedback) {
        feedbackService.updateFeedback(feedbackId, updatedFeedback);
        return new ResponseEntity<>("Feedback Data Updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{foodId}")
    public ResponseEntity<String> deleteFeedback(@PathVariable UUID feedbackId) {
        try {
            feedbackService.deleteFeedback(feedbackId);
            return new ResponseEntity<>("Feedback Data Deleted Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete feedback data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("gethealth/{userId}")
    public ResponseEntity<?> getHealthById(@PathVariable UUID userId) {
        HealthDTO health = feedbackService.getHealthById(userId);

        if (health != null) {
            return new ResponseEntity<>(health, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
