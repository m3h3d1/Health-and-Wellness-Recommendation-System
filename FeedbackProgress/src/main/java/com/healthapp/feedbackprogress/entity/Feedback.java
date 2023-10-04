package com.healthapp.feedbackprogress.entity;

import com.healthapp.feedbackprogress.enumss.RecommendationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "feedback_id")
    private UUID feedbackId;

    @Column(name = "user_id")
    private UUID userId;

    @JoinColumn(name = "recommendation_id")
    private UUID recommendationId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recommendation_type")
    private RecommendationType recommendationType;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment")
    private String comment;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;
}