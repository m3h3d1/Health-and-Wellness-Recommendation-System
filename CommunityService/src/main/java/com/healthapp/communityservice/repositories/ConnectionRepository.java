package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConnectionRepository extends JpaRepository<Connection, Integer> {
    List<Connection> findByFollowerId(UUID followerId);
}
