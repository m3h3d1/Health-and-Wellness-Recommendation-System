package com.healthapp.communityservice.models.commentdto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class CommentUpdateDTO {
    private String content;
}
