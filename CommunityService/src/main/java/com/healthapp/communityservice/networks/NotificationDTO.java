package com.healthapp.communityservice.networks;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @RequiredArgsConstructor
public class NotificationDTO {
    private String text;
    private LocalDateTime timeCreate;
    private String type;
}
