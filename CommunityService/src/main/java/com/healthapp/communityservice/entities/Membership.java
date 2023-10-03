package com.healthapp.communityservice.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.healthapp.communityservice.enums.GroupMemberRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID membershipId;
    GroupMemberRole role;
    private UUID userId;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "community_id")
    private Group community;
}
