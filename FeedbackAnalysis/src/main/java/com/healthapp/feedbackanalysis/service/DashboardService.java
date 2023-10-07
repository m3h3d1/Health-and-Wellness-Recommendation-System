package com.healthapp.feedbackanalysis.service;

import com.healthapp.feedbackanalysis.dto.FeedbackDTO;

import java.util.List;

public interface DashboardService {
    public List<FeedbackDTO> getAllFeedbacks();
    public List<FeedbackDTO> trends();
}
