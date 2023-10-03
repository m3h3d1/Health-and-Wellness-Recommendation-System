package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.models.groupdto.GroupDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Component
public class GroupMapper {
    public Group getGroup(GroupDTO groupDto) {
        Group group = new Group();

        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        group.setPosts(new ArrayList<>());
        group.setLastActivity(LocalDateTime.now());
        group.setTimeCreated(LocalDateTime.now());
        group.setPosts(new ArrayList<>());

        return group;
    }
}
