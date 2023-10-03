package com.healthapp.communityservice.utilities.mapping;

import com.healthapp.communityservice.entities.Comment;
import com.healthapp.communityservice.entities.Group;
import com.healthapp.communityservice.entities.Post;
import com.healthapp.communityservice.enums.PostPrivacy;
import com.healthapp.communityservice.models.commentdto.CommentReadDTO;
import com.healthapp.communityservice.models.postdto.PostCreateDTO;
import com.healthapp.communityservice.models.postdto.PostReadDTO;
import com.healthapp.communityservice.services.interfaces.GroupService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostMapper {

    private final CommentMapper commentMapping;
    private final GroupService groupService;

    public PostMapper(CommentMapper commentMapping, GroupService groupService) {
        this.commentMapping = commentMapping;
        this.groupService = groupService;
    }

    public PostReadDTO getPostRead(Post post) {
        PostReadDTO postReadDTO = new PostReadDTO();
        postReadDTO.setPostId(post.getPostId());
        postReadDTO.setAuthorFullName(post.getAuthorFullName());
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

    public Post getPost(PostCreateDTO postDTO){
        Post post = new Post();
        post.setComments(new ArrayList<>());
        post.setDislikes(new ArrayList<>());
        post.setLikes(new ArrayList<>());
        post.setContent(postDTO.getContent());
        post.setPrivacy(postDTO.getPrivacy());
        post.setTimeCreated(LocalDateTime.now());
        post.setUserId(postDTO.getUserId());

        // Setting up the post author name;
        post.setAuthorFullName("Name Unavailable");
        // Make internal API call to get the post author name

        //Setting up the post group
        if(postDTO.getPrivacy().equals(PostPrivacy.GROUP)){
            Group group = groupService.getById(postDTO.getGroupId());
            post.setCommunity(group);
        }

        return post;
    }
}
