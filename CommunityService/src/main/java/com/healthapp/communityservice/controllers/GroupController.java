package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.models.groupdto.GroupDTO;
import com.healthapp.communityservice.services.interfaces.GroupService;
import com.healthapp.communityservice.utilities.token.IDExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/community/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // Create a new group by providing group details in the request body
    @PostMapping
    ResponseEntity<String> create(@RequestBody GroupDTO groupDTO){
        groupService.create(groupDTO, IDExtractor.getUserID());
        return ResponseEntity.ok("Group created successfully");
    }

    // Get a list of all groups
    @GetMapping
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(groupService.getAll());
    }

    // Get information about a group by specifying its unique ID
    @GetMapping("/{groupId}")
    ResponseEntity<?> readById(@PathVariable UUID groupId){
        return ResponseEntity.ok(groupService.getById(groupId));
    }

    // Update group information by specifying the group's ID and providing updated details in the request body
    @PutMapping("/{groupId}")
    ResponseEntity<String> update(@PathVariable UUID groupId, @RequestBody GroupDTO groupDTO){
        groupService.update(groupId, IDExtractor.getUserID(), groupDTO);
        return ResponseEntity.ok("Group updated successfully");
    }

    // Delete a group by specifying its unique ID
    @DeleteMapping("/{groupId}")
    ResponseEntity<String> delete(@PathVariable UUID groupId){
        groupService.delete(groupId, IDExtractor.getUserID());
        return ResponseEntity.ok("Group deleted");
    }

    // Add a member to a group by specifying the group ID, user ID, and member role
    @PostMapping("/add-member/{groupId}/{userId}/{role}")
    ResponseEntity<String> addMember(@PathVariable UUID groupId, @PathVariable UUID userId, @PathVariable GroupMemberRole role){
        groupService.addMember(groupId, userId, role);
        return ResponseEntity.ok("Operation successful");
    }

    // Remove a member from a group by specifying the group ID and user ID
    @DeleteMapping("/remove-member/{groupId}/{userId}")
    ResponseEntity<String> removeMember(@PathVariable UUID groupId, @PathVariable UUID userId){
        groupService.removeMember(groupId, userId);
        return ResponseEntity.ok("Operation successful");
    }
}
