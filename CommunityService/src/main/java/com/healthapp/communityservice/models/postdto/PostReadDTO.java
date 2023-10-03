package com.healthapp.communityservice.models.postdto;

import com.healthapp.communityservice.enums.PostPrivacy;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class PostReadDTO {
    private UUID postId;
    private String authorFullName;
    private String content;
    private LocalDateTime timeCreated;
    private PostPrivacy privacy;
    private List<CommentReadDTO> comments;
    private Integer likes;
    private Integer dislikes;
}
