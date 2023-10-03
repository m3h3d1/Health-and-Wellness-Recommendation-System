package com.healthapp.communityservice.services.interfaces;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Membership;
import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.models.groupdto.GroupDTO;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public interface GroupService {
    public void create(GroupDTO groupDto);
    public boolean ifExists(UUID groupId);
    public List<Group> getALl();
    public Group getById(UUID groupId);
    public void update(UUID groupId, GroupDTO groupDTO);
    public void delete(UUID groupId);
    public void addMember(UUID groupId, UUID userId, GroupMemberRole role);
    public void removeMember(UUID groupId, UUID userId);
}
