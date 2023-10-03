package com.healthapp.communityservice.entities;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class AchievementProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "achievement_progress_id")
    private UUID achievementProgressId;
    private Double score;

    @ManyToOne
    @JoinColumn(name = "achievement_id")
    private Achievement achievement;

    @ManyToOne
    @JoinColumn(name = "achievement_stats_id")
    private AchievementStatistics statistics;
}
