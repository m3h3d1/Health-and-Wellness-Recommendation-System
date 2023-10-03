package com.healthapp.communityservice.models.commentdto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class CommentCreateDTO {
    private String content;
    private Boolean isReply;
    private UUID parentCommentId;
    private UUID userId;
    private UUID parentPostId;
}
