package com.healthapp.feedbackanalysis.controller;

import com.healthapp.feedbackanalysis.dto.FeedbackDTO;
import com.healthapp.feedbackanalysis.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    DashboardService dashboardService;

    @GetMapping("/feedback")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedback() {
        try {
            List<FeedbackDTO> feedbackList = dashboardService.getAllFeedbacks();
            if (feedbackList != null && !feedbackList.isEmpty()) {
                return new ResponseEntity<>(feedbackList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching feedback: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/progress")
    public ResponseEntity<List<FeedbackDTO>> monitorUserProgressAndTrends() {
        try {
            List<FeedbackDTO> feedbackList = dashboardService.trends();

            if (feedbackList != null && !feedbackList.isEmpty()) {
                return new ResponseEntity<>(feedbackList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            System.err.println("Error while fetching feedback: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}