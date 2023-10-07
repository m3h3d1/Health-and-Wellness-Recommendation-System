package com.healthapp.userservice.service.interfaces;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.model.Requestdto.ContactRequestDto;
import com.healthapp.userservice.model.Responsedto.ContactResponseDto;
import com.healthapp.userservice.model.updatedeletedto.ContactUpdateDto;

import java.util.List;
import java.util.UUID;

public interface ContactService {
    void addContact(ContactRequestDto contactRequestDto);
    void updateContact(ContactUpdateDto contactUpdateDto);
    ContactResponseDto getContactById(UUID userId);
    List<Contact> getAllContacts();
}
