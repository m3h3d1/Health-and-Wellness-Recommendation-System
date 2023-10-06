package com.healthapp.feedbackprogress.service.implementation;

import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;
import com.healthapp.feedbackprogress.entity.Feedback;
import com.healthapp.feedbackprogress.exception.FeedbackNotFoundException;
import com.healthapp.feedbackprogress.exception.FeedbackServiceException;
import com.healthapp.feedbackprogress.network.HealthServiceProxy;
import com.healthapp.feedbackprogress.repository.FeedbackRepository;
import com.healthapp.feedbackprogress.service.FeedbackService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private HealthServiceProxy healthServiceProxy;

    @Override
    public Feedback addFeedback(Feedback feedback) {
        feedback.setFeedbackDate(LocalDateTime.now());
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    @Override
    @Transactional
    public Feedback updateFeedback(UUID feedbackId, Feedback updatedFeedback) {
        Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new FeedbackNotFoundException("Feedback data not found with ID: " + feedbackId));

        existingFeedback.setRating(updatedFeedback.getRating());
        existingFeedback.setComment(updatedFeedback.getComment());
        existingFeedback.setFeedbackDate(LocalDateTime.now());
        return feedbackRepository.save(existingFeedback);
    }

    @Override
    public void deleteFeedback(UUID feedbackId) {
        try {
            Feedback existingFeedback = feedbackRepository.findById(feedbackId)
                    .orElseThrow(() -> new FeedbackNotFoundException("Feedback data not found with ID: " + feedbackId));

            feedbackRepository.delete(existingFeedback);
        } catch (Exception e) {
            throw new FeedbackServiceException("Failed to delete feedback data. " + e.getMessage());
        }
    }

    @Override
    public HealthDTO getHealthById(UUID userId) {
        return healthServiceProxy.getHealth(userId).getBody();
    }

//    @Override
//    public Food getFoodById(UUID foodId) {
//
//        return foodRepository.findById(foodId).orElse(null);
//    }

}
