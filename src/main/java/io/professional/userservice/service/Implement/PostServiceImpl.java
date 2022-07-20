package io.professional.userservice.service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Post;
import io.professional.userservice.respository.PostRepository;
import io.professional.userservice.service.PostService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    final PostRepository postRepository;

    @Override
    public Post getPostById(String id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getAllPostsByOwnerId(String ownerId) {
        //return postRepository.findByOwnerId(ownerId);
        return postRepository.findByOwnerIdOrderByCreatedDateDesc(ownerId);
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getPostsFromFollowingUser(List<String> followingList) {
        //return postRepository.findByOwnerIdIn(followingList);
        return postRepository.findByOwnerIdInOrderByCreatedDateDesc(followingList);
    }
    
}
