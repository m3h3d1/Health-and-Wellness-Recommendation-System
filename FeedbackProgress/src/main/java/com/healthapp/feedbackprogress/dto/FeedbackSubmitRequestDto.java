package com.healthapp.feedbackprogress.dto;

import com.healthapp.feedbackprogress.enumss.RecommendationType;
import jakarta.persistence.GeneratedValue;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

public class FeedbackSubmitRequestDto {
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID feedbackId;
    private RecommendationType recommendationType;
    private int rating;
    private String comment;
}
