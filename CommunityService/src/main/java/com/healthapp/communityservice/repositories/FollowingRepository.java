package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.Following;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowingRepository extends JpaRepository<Following, Integer> {
}
