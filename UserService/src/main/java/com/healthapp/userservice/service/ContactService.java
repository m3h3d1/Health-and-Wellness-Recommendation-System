package com.healthapp.userservice.service;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.model.ContactRequestDto;
import com.healthapp.userservice.model.ContactResponseDto;
import com.healthapp.userservice.model.ContactUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    void addContact(ContactRequestDto contactRequestDto);
    void updateContact(ContactUpdateDto contactUpdateDto, UUID userId);
    ContactResponseDto getContactById(UUID userId);
    List<Contact> getAllContacts();
}
