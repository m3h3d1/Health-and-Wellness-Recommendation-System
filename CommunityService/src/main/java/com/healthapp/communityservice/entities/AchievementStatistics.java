package com.healthapp.communityservice.entities;

import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class AchievementStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID achievementStatsId;

    @OneToMany(mappedBy = "statistics", cascade = CascadeType.ALL)
    private List<AchievementProgress> progresses;
    private UUID userId;
}
