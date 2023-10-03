package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
