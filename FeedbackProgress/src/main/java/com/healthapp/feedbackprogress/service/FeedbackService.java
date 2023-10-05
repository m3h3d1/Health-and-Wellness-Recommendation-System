package com.healthapp.feedbackprogress.service;

import com.healthapp.feedbackprogress.dto.healthdto.HealthDTO;
import com.healthapp.feedbackprogress.entity.Feedback;

import java.util.List;
import java.util.UUID;

public interface FeedbackService {
    public Feedback addFeedback(Feedback feedback);
    public List<Feedback> getAllFeedbacks();
    public Feedback updateFeedback(UUID feedbackId, Feedback updatedFeedback);
    public void deleteFeedback(UUID feedbackId);

    public HealthDTO getHealthById(UUID userId);
}
