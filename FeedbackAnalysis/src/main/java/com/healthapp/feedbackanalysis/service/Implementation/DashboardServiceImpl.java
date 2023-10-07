package com.healthapp.feedbackanalysis.service.Implementation;

import com.healthapp.feedbackanalysis.dto.FeedbackDTO;
import com.healthapp.feedbackanalysis.exception.FeedbackNotFoundException;
import com.healthapp.feedbackanalysis.network.FeedbackProgressServiceProxy;
import com.healthapp.feedbackanalysis.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    FeedbackProgressServiceProxy feedbackProgressServiceProxy;

    @Override
    public List<FeedbackDTO> getAllFeedbacks() {
        try {
            ResponseEntity<List<FeedbackDTO>> responseEntity = feedbackProgressServiceProxy.getAllFeedback();

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                List<FeedbackDTO> feedbackList = responseEntity.getBody();
                if (feedbackList != null && !feedbackList.isEmpty()) {
                    return feedbackList;
                }
            }

            throw new FeedbackNotFoundException("Feedback not found.");
        } catch (Exception e) {
            throw new FeedbackNotFoundException("Failed to retrieve feedback data: " + e.getMessage());
        }
    }

    @Override
    public List<FeedbackDTO> trends() { //
        try {
            List<FeedbackDTO> feedbackList = feedbackProgressServiceProxy.getAllFeedback().getBody();

            Collections.sort(feedbackList, Comparator.comparingInt(FeedbackDTO::getRating).reversed());

            // Return the top 10 feedbacks or all if there are fewer than 10
            int limit = Math.min(feedbackList.size(), 10);
            return feedbackList.subList(0, limit);
        } catch (Exception e) {
            throw new FeedbackNotFoundException("Feedback Not Found. " + e.getMessage());
        }
    }
}
