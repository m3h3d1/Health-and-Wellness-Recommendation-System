package com.healthapp.userservice.controller;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.model.ContactRequestDto;
import com.healthapp.userservice.model.ContactResponseDto;
import com.healthapp.userservice.model.ContactUpdateDto;
import com.healthapp.userservice.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/contact")
public class ContactController {
    @Autowired
    private ContactService contactService;
    @PostMapping("/create")
    public ResponseEntity<String> addContact(@RequestBody ContactRequestDto contactRequestDto) {
        contactService.addContact(contactRequestDto);
        return new ResponseEntity<>("Contact Created successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateContact(
            @RequestBody ContactUpdateDto contactUpdateDto,
            @PathVariable UUID userId
    ) {
        contactService.updateContact(contactUpdateDto, userId);
        return new ResponseEntity<>("Contact Updated Successfully!",HttpStatus.OK);
    }

    @GetMapping("/read-by-id/{userId}")
    public ResponseEntity<ContactResponseDto> getContactById(@PathVariable UUID userId) {
        ContactResponseDto contactResponseDto = contactService.getContactById(userId);
        if (contactResponseDto != null) {
            return new ResponseEntity<>(contactResponseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<Contact>> getAllContacts(){
        return new ResponseEntity<>(contactService.getAllContacts(),HttpStatus.OK);
    }
}
