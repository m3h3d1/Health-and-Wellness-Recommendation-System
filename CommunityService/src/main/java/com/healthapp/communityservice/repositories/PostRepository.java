package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.Connection;
import com.healthapp.communityservice.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByUserId(UUID userId);
}
