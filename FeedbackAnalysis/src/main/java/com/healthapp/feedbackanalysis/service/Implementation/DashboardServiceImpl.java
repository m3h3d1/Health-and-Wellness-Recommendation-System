package com.healthapp.feedbackanalysis.service.Implementation;

import com.healthapp.feedbackanalysis.dto.FeedbackDTO;
import com.healthapp.feedbackanalysis.exception.FeedbackNotFoundException;
import com.healthapp.feedbackanalysis.network.FeedbackProgressServiceProxy;
import com.healthapp.feedbackanalysis.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public List<FeedbackDTO> trends() {
        try {
            List<FeedbackDTO> feedbackList = feedbackProgressServiceProxy.getAllFeedback().getBody();

            // Filter feedbacks with ratings between 4 and 5
            List<FeedbackDTO> filteredFeedbacks = new ArrayList<>();
            for (FeedbackDTO feedback : feedbackList) {
                int rating = feedback.getRating();
                if (rating >= 4 && rating <= 5) {
                    filteredFeedbacks.add(feedback);
                }
            }

            // Calculate the frequency of each rating and recommendation type
            Map<Integer, Map<String, Integer>> ratingAndTypeFrequencyMap = new HashMap<>();
            for (FeedbackDTO feedback : filteredFeedbacks) {
                int rating = feedback.getRating();
                String recommendationType = feedback.getRecommendationType().name();

                // Update frequency map
                if (!ratingAndTypeFrequencyMap.containsKey(rating)) {
                    ratingAndTypeFrequencyMap.put(rating, new HashMap<>());
                }
                if (!ratingAndTypeFrequencyMap.get(rating).containsKey(recommendationType)) {
                    ratingAndTypeFrequencyMap.get(rating).put(recommendationType, 1);
                } else {
                    int currentFrequency = ratingAndTypeFrequencyMap.get(rating).get(recommendationType);
                    ratingAndTypeFrequencyMap.get(rating).put(recommendationType, currentFrequency + 1);
                }
            }

            // Sort ratings by the most frequent recommendation type
            List<Integer> sortedRatings = ratingAndTypeFrequencyMap.entrySet().stream()
                    .sorted((entry1, entry2) -> {
                        int frequency1 = Collections.max(entry1.getValue().values());
                        int frequency2 = Collections.max(entry2.getValue().values());
                        return Integer.compare(frequency2, frequency1);
                    })
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            // Sort feedbacks based on the matching rating order
            List<FeedbackDTO> sortedFeedbacks = new ArrayList<>();
            for (int rating : sortedRatings) {
                for (FeedbackDTO feedback : filteredFeedbacks) {
                    if (feedback.getRating() == rating) {
                        sortedFeedbacks.add(feedback);
                    }
                }
            }

            // Return the top 10 feedbacks or all if fewer than 10
            int limit = Math.min(sortedFeedbacks.size(), 10);
            return sortedFeedbacks.subList(0, limit);
        } catch (Exception e) {
            throw new FeedbackNotFoundException("Feedback Not Found. " + e.getMessage());
        }
    }
}
