package com.healthapp.userservice.service.implementation;

import com.healthapp.userservice.domain.Contact;
import com.healthapp.userservice.domain.UserEntity;
import com.healthapp.userservice.exception.ContactUpdateException;
import com.healthapp.userservice.model.Requestdto.ContactRequestDto;
import com.healthapp.userservice.model.Responsedto.ContactResponseDto;
import com.healthapp.userservice.model.updatedeletedto.ContactUpdateDto;
import com.healthapp.userservice.repository.ContactRepository;
import com.healthapp.userservice.repository.UserRepository;
import com.healthapp.userservice.service.interfaces.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactServiceImpl implements ContactService {
    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addContact(ContactRequestDto contactRequestDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(contactRequestDto.getUserId());
        if(optionalUser.isPresent()) {
            Contact contact = new Contact();
            contact.setUserId(contactRequestDto.getUserId());
            contact.setPrimaryPhoneNumber(contactRequestDto.getPrimaryPhoneNumber());
            contact.setOptionalPhoneNumber(contactRequestDto.getOptionalPhoneNumber());
            contact.setCountry(contactRequestDto.getCountry());
            contact.setCity(contactRequestDto.getCity());
            contact.setArea(contactRequestDto.getArea());
            contact.setRoadNumber(contactRequestDto.getRoadNumber());
            contact.setBlockNumber(contactRequestDto.getBlockNumber());
            contact.setHouseNumber(contactRequestDto.getHouseNumber());
            contactRepository.save(contact);
            optionalUser.get().setContact(contact);
            userRepository.save(optionalUser.get());
        }
        else{
            throw new EmptyResultDataAccessException("User",1);
        }
    }

    @Override
    public void updateContact(ContactUpdateDto contactUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> user = userRepository.findById(UUID.fromString(authentication.getName()));
        user.ifPresent(userEntity -> contactRepository.findByUserId(UUID.fromString(authentication.getName())).ifPresent(contact -> {
            try {
                if (contactUpdateDto.getPrimaryPhoneNumber() != null) {
                    contact.setPrimaryPhoneNumber(contactUpdateDto.getPrimaryPhoneNumber());
                }
                if (contactUpdateDto.getOptionalPhoneNumber() != null) {
                    contact.setOptionalPhoneNumber(contactUpdateDto.getOptionalPhoneNumber());
                }
                if (contactUpdateDto.getCountry() != null) {
                    contact.setCountry(contactUpdateDto.getCountry());
                }
                if (contactUpdateDto.getCity() != null) {
                    contact.setCity(contactUpdateDto.getCity());
                }
                if (contactUpdateDto.getArea() != null) {
                    contact.setArea(contactUpdateDto.getArea());
                }
                if (contactUpdateDto.getRoadNumber() != null) {
                    contact.setRoadNumber(contactUpdateDto.getRoadNumber());
                }
                if (contactUpdateDto.getBlockNumber() != null) {
                    contact.setBlockNumber(contactUpdateDto.getBlockNumber());
                }
                if (contactUpdateDto.getHouseNumber() != null) {
                    contact.setHouseNumber(contactUpdateDto.getHouseNumber());
                }
                contactRepository.save(contact);
                userEntity.setContact(contact);
                userRepository.save(userEntity);
            } catch (Exception ex) {
                throw new ContactUpdateException();
            }
        }));
    }



    @Override
    public ContactResponseDto getContactById(UUID userId) {
        Optional<Contact> optionalContact=contactRepository.findByUserId(userId);
        if(optionalContact.isPresent()){
            Contact contact = optionalContact.get();
            ContactResponseDto responseDto = new ContactResponseDto();
            responseDto.setPrimaryPhoneNumber(contact.getPrimaryPhoneNumber());
            responseDto.setOptionalPhoneNumber(contact.getOptionalPhoneNumber());
            responseDto.setCountry(contact.getCountry());
            responseDto.setCity(contact.getCity());
            responseDto.setArea(contact.getArea());
            responseDto.setRoadNumber(contact.getRoadNumber());
            responseDto.setBlockNumber(contact.getBlockNumber());
            responseDto.setHouseNumber(contact.getHouseNumber());
            return responseDto;
        }
        else{
            throw new EmptyResultDataAccessException("User",1);
        }
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
