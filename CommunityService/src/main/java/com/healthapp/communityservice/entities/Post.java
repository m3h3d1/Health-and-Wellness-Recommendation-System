package com.healthapp.communityservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.healthapp.communityservice.enums.PostPrivacy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor @Entity  @Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "post_id")
    private UUID postId;

    private String authorFullName;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime timeCreated;
    private PostPrivacy privacy;

    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Interact> likes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Interact> dislikes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Interact> followers;

    // Parent references
    private UUID userId;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "community_id")
    private Group community;
}