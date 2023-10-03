package com.healthapp.communityservice.models.commentdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class CommentReadDTO {
    private UUID commentId;
    private String userFullName;
    private String content;
    private LocalDateTime timeCreated;
    private Integer likes;
    private Integer dislikes;
    private List<CommentReadDTO> replies;
}