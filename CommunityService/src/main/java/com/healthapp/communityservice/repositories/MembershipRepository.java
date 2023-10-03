package com.healthapp.communityservice.repositories;

import com.healthapp.communityservice.entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, UUID> {
}
