package com.healthapp.userservice.repository;

import com.healthapp.userservice.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
    Optional<Contact> findByUserId(UUID userId);
}
