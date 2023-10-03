package com.healthapp.communityservice.controllers;

import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.models.groupdto.GroupDTO;
import com.healthapp.communityservice.services.interfaces.GroupService;
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

    @PostMapping
    ResponseEntity<String> create(@RequestBody GroupDTO groupDTO){
        groupService.create(groupDTO);
        return ResponseEntity.ok("Group created successfully");
    }

    @GetMapping
    ResponseEntity<?> getAll() {
        return ResponseEntity.ok(groupService.getALl());
    }

    @GetMapping("/{groupId}")
    ResponseEntity<?> readById(@PathVariable UUID groupId){
        return ResponseEntity.ok(groupService.getById(groupId));
    }

    @PutMapping("/{groupId}")
    ResponseEntity<String> update(@PathVariable UUID groupId, @RequestBody GroupDTO groupDTO){
        groupService.update(groupId, groupDTO);
        return ResponseEntity.ok("Group updated successfully");
    }

    @DeleteMapping("/{groupId}")
    ResponseEntity<String> delete(@PathVariable UUID groupId){
        groupService.delete(groupId);
        return ResponseEntity.ok("Group deleted");
    }

    @PostMapping("/add-member/{groupId}/{userId}/{role}")
    ResponseEntity<String> addMember(@PathVariable UUID groupId, @PathVariable UUID userId, @PathVariable GroupMemberRole role){
        groupService.addMember(groupId, userId, role);
        return ResponseEntity.ok("Operation successful");
    }

    @DeleteMapping("/remove-member/{groupId}/{userId}")
    ResponseEntity<String> removeMember(@PathVariable UUID groupId, @PathVariable UUID userId){
        groupService.removeMember(groupId, userId);
        return ResponseEntity.ok("Operation successful");
    }
}
