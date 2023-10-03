package com.healthapp.communityservice.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity @Getter @Setter @RequiredArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "follow_id")
    private Integer followId;

    private UUID followerId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Following> following;
}
