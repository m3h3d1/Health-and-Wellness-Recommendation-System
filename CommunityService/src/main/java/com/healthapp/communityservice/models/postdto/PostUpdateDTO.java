package com.healthapp.communityservice.models.postdto;

import com.healthapp.communityservice.enums.PostPrivacy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter @RequiredArgsConstructor
public class PostUpdateDTO {
    private String content;
    private PostPrivacy privacy;
}