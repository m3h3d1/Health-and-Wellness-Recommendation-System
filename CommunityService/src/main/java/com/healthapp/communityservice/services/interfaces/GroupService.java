package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Membership;
import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.models.groupdto.GroupDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface GroupService {
    // Group crud operations
    public void create(GroupDTO groupDto, UUID ownerId);
    public boolean ifExists(UUID groupId);
    public List<Group> getAll();
    public Group getById(UUID groupId);
    public void update(UUID groupId, UUID requestUserId, GroupDTO groupDTO);
    public void delete(UUID groupId, UUID requestUserId);
    public void addMember(UUID groupId, UUID userId, GroupMemberRole role);
    public void removeMember(UUID groupId, UUID userId);
}
