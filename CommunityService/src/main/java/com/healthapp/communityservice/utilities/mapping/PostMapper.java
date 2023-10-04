package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.enums.PostPrivacy;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.networks.UserDTO;
import com.healthapp.communityservice.networks.UserServiceProxy;
import com.healthapp.communityservice.services.interfaces.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PostMapper {

    private final CommentMapper commentMapping;
    private final GroupService groupService;
    private final UserServiceProxy userServiceProxy;

    public PostMapper(CommentMapper commentMapping, GroupService groupService,
                      UserServiceProxy userServiceProxy) {
        this.commentMapping = commentMapping;
        this.groupService = groupService;
        this.userServiceProxy = userServiceProxy;
    }

    /**
     * Map a Post entity to a PostReadDTO.
     *
     * @param post The Post entity to map.
     * @return A PostReadDTO representing the mapped Post entity.
     */
    public PostReadDTO getPostRead(Post post) {
        PostReadDTO postReadDTO = new PostReadDTO();
        postReadDTO.setPostId(post.getPostId());

        // Set up the name of the user
        postReadDTO.setAuthorFullName(post.getAuthorFullName());
        if (post.getAuthorFullName() == null || post.getAuthorFullName().equals("Name Unavailable")) {
            try {
                ResponseEntity<UserDTO> userResponse = userServiceProxy.getUser(post.getUserId());
                String firstName = Objects.requireNonNull(userResponse.getBody()).getFirstName();
                String lastName = Objects.requireNonNull(userResponse.getBody()).getLastName();
                post.setAuthorFullName(firstName + " " + lastName);
            } catch (Exception e) {
                post.setAuthorFullName("Name Unavailable");
            }
        }

        postReadDTO.setAuthorId(post.getUserId());
        postReadDTO.setContent(post.getContent());
        postReadDTO.setTimeCreated(post.getTimeCreated());
        postReadDTO.setPrivacy(post.getPrivacy());
        postReadDTO.setLikes(post.getLikes() != null ? post.getLikes().size() : 0);
        postReadDTO.setDislikes(post.getDislikes() != null ? post.getDislikes().size() : 0);

        // Mapping comments using CommentMapping
        List<CommentReadDTO> commentDTOs = new ArrayList<>();
        for (Comment comment : post.getComments()) {
            commentDTOs.add(commentMapping.getCommentRead(comment));
        }
        postReadDTO.setComments(commentDTOs);

        return postReadDTO;
    }

    /**
     * Map a PostCreateDTO to a Post entity.
     *
     * @param postDTO The PostCreateDTO to map.
     * @return A Post entity representing the mapped PostCreateDTO.
     */
    public Post getPost(PostCreateDTO postDTO) {
        Post post = new Post();
        post.setComments(new ArrayList<>());
        post.setDislikes(new ArrayList<>());
        post.setLikes(new ArrayList<>());
        post.setContent(postDTO.getContent());
        post.setPrivacy(postDTO.getPrivacy());
        post.setTimeCreated(LocalDateTime.now());
        post.setUserId(postDTO.getUserId());

        // Setting up the post author name
        try {
            ResponseEntity<UserDTO> userResponse = userServiceProxy.getUser(postDTO.getUserId());
            String firstName = Objects.requireNonNull(userResponse.getBody()).getFirstName();
            String lastName = userResponse.getBody().getLastName();
            post.setAuthorFullName(firstName + " " + lastName);
        } catch (Exception e) {
            post.setAuthorFullName("Name Unavailable");
        }

        // Setting up the post group
        if (postDTO.getPrivacy().equals(PostPrivacy.GROUP)) {
            Group group = groupService.getById(postDTO.getGroupId());
            post.setCommunity(group);
        }

        return post;
    }
}
