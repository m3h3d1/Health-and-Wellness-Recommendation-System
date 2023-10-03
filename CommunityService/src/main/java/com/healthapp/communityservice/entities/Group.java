package com.healthapp.communityservice.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "community")
public class Group {
    @Id
    @Column(name = "community_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID communityId;

    private String name;
    private String description;
    private LocalDateTime timeCreated;
    private LocalDateTime lastActivity;
    @OneToMany(mappedBy = "community",cascade = CascadeType.ALL)
    List<Post> posts;
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<Membership> members;
}
