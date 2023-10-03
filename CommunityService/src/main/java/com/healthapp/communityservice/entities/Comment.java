package com.healthapp.communityservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "comment_id")
    private UUID commentId;

    @Column(name = "commented_by")
    private String userFullName;
    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime timeCreated;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Interact> likes;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Interact> dislikes;

    // Relational references
    // Recursive self referencing relation
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    private UUID userId;
    @ManyToOne
    @JoinColumn(name = "parent_post_id")
    @JsonIgnore
    private Post parentPost;
}

