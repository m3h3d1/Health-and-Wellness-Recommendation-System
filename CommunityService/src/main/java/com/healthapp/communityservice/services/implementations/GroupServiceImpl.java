package com.healthapp.communityservice.services.implementations;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Membership;
import com.healthapp.communityservice.enums.GroupMemberRole;
import com.healthapp.communityservice.exceptions.InteractionBlockedException;
import com.healthapp.communityservice.exceptions.NotFoundException;
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

    /**
     * Create a new group.
     *
     * @param groupDto The DTO containing group creation data.
     * @param ownerId The ID of the user who creates the group.
     */
    public void create(GroupDTO groupDto, UUID ownerId) {
        Group group = groupMapping.getGroup(groupDto);
        group.setOwnerId(ownerId);
        groupRepository.save(group);
    }

    /**
     * Check if a group exists by its ID.
     *
     * @param groupId The UUID of the group to check.
     * @return True if the group exists, false otherwise.
     * @throws NotFoundException If the group does not exist.
     */
    public boolean ifExists(UUID groupId){
        if(groupRepository.existsById(groupId)) return true;
        else {
            throw new NotFoundException("GroupNotFoundException", "Fetching a group info.", "Group with id " + groupId + " does not exist.");
        }
    }

    /**
     * Get a list of all groups.
     *
     * @return A list of Group objects representing all groups.
     */
    public List<Group> getAll(){
        return groupRepository.findAll();
    }

    /**
     * Get a group by its ID.
     *
     * @param groupId The UUID of the group to retrieve.
     * @return The Group object representing the group.
     */
    public Group getById(UUID groupId) {
        if(ifExists(groupId)) return groupRepository.findById(groupId).get();
        else return null;
    }

    /**
     * Update group information.
     *
     * @param groupId   The UUID of the group to update.
     * @param requestUserId The UUID of the user who requested the update.
     * @param groupDTO  The DTO containing updated group data.
     */
    public void update(UUID groupId, UUID requestUserId, GroupDTO groupDTO){
        Group group = getById(groupId);
        if(!group.getOwnerId().equals(requestUserId)){
            throw new InteractionBlockedException("The user doesn't own the group.");
        }
        group.setName(groupDTO.getName());
        group.setDescription(groupDTO.getDescription());
        groupRepository.save(group);
    }

    /**
     * Delete a group by its ID.
     *
     * @param requestUserId The UUID of the user who requested to delete.
     * @param groupId The UUID of the group to delete.
     */
    public void delete(UUID groupId, UUID requestUserId){
        if(!ifExists(groupId)) return;
        Group group = getById(groupId);
        if(!group.getOwnerId().equals(requestUserId)){
            throw new InteractionBlockedException("The user doesn't own the group.");
        }
        groupRepository.deleteById(groupId);
    }

    /**
     * Add a member to a group.
     *
     * @param groupId The UUID of the group.
     * @param userId  The UUID of the user to add.
     * @param role    The role of the user in the group.
     */
    public void addMember(UUID groupId, UUID userId, GroupMemberRole role){
        if(!ifExists(groupId)) return;
        Group group = getById(groupId);
        List<Membership> members = group.getMembers().stream().filter(member -> member.getUserId().equals(userId)).collect(Collectors.toList());
        if(!members.isEmpty()) {
            throw new InteractionBlockedException("User is already in the group.");
        }
        Membership membership = new Membership();
        membership.setUserId(userId);
        membership.setRole(role);
        membership.setCommunity(group);
        membershipRepository.save(membership);
    }

    /**
     * Remove a member from a group.
     *
     * @param groupId The UUID of the group.
     * @param userId  The UUID of the user to remove.
     */
    public void removeMember(UUID groupId, UUID userId){
        if(!ifExists(groupId)) return;
        Group group = getById(groupId);
        Membership member = null;
        List<Membership> members = group.getMembers().stream().filter(m -> m.getUserId().equals(userId)).collect(Collectors.toList());
        if(members.isEmpty()) throw new InteractionBlockedException("User is not a member of the group.");
        member = members.get(0);
        // Remove the dependency
        group.getMembers().remove(member);
        // Delete the record from the database
        membershipRepository.delete(member);
    }
}
