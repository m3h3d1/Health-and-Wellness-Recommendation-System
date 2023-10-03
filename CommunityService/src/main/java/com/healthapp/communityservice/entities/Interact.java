package com.healthapp.communityservice.entities;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Interact {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer interactId;
    private UUID userId;
}
