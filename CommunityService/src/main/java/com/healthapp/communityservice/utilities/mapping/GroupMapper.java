package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.models.groupdto.GroupDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class GroupMapper {

    /**
     * Map a GroupDTO to a Group entity.
     *
     * @param groupDto The GroupDTO to map.
     * @return A Group entity representing the mapped GroupDTO.
     */
    public Group getGroup(GroupDTO groupDto) {
        Group group = new Group();

        // Set the name and description from the DTO
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());

        // Initialize other properties
        group.setPosts(new ArrayList<>());
        group.setLastActivity(LocalDateTime.now());
        group.setTimeCreated(LocalDateTime.now());
        group.setPosts(new ArrayList<>());

        return group;
    }
}
