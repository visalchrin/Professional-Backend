package io.professional.userservice.service;

import java.util.List;

import io.professional.userservice.domain.Post;

public interface PostService {
    Post getPostById(String id);
    List<Post> getAllPostsByOwnerId(String ownerId);
    Post createPost(Post post);
    List<Post> getPostsFromFollowingUser(List<String> followingList);
}
