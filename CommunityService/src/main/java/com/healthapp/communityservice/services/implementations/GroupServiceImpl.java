package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Membership;
import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.models.groupdto.GroupDTO;
import com.healthapp.communityservice.repositories.GroupRepository;
import com.healthapp.communityservice.repositories.MembershipRepository;
import com.healthapp.communityservice.services.interfaces.GroupService;
import com.healthapp.communityservice.utilities.mapping.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final GroupMapper groupMapping;

    public GroupServiceImpl(GroupRepository groupRepository, MembershipRepository membershipRepository, GroupMapper groupMapping) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.groupMapping = groupMapping;
    }

    public void create(GroupDTO groupDto) {
        Group group = groupMapping.getGroup(groupDto);
        groupRepository.save(group);
    }

    public boolean ifExists(UUID groupId){
        return groupRepository.existsById(groupId);
    }

    public List<Group> getALl(){
        return groupRepository.findAll();
    }

    public Group getById(UUID groupId) {
        if(ifExists(groupId)) return groupRepository.findById(groupId).get();
        else return null;
    }

    public void update(UUID groupId, GroupDTO groupDTO){
        Group group = getById(groupId);
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        groupRepository.save(group);
    }

    public void delete(UUID groupId){
        if(!ifExists(groupId)) return;
        groupRepository.deleteById(groupId);
    }

    public void addMember(UUID groupId, UUID userId, GroupMemberRole role){
        if(!ifExists(groupId)) return;
        Group group = getById(groupId);
        List<Membership> members = group.getMembers().stream().filter(member -> member.getUserId().equals(userId)).collect(Collectors.toList());
        if(!members.isEmpty()) return;
        Membership membership = new Membership();
        membership.setUserId(userId);
        membership.setRole(role);
        membership.setCommunity(group);
        membershipRepository.save(membership);
    }

    public void removeMember(UUID groupId, UUID userId){
        if(!ifExists(groupId)) return;
        Group group = getById(groupId);
        Membership member = null;
        List<Membership> members = group.getMembers().stream().filter(m -> m.getUserId().equals(userId)).collect(Collectors.toList());
        if(members.isEmpty()) return;
        member = members.get(0);
        // Remove the dependency
        group.getMembers().remove(member);
        // Delete the record form database
        membershipRepository.delete(member);
    }
}
