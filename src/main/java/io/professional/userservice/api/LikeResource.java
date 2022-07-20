package io.professional.userservice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.professional.userservice.domain.AppUser;
import io.professional.userservice.domain.Likes;
import io.professional.userservice.domain.Post;
import io.professional.userservice.service.LikeService;
import io.professional.userservice.service.PostService;
import io.professional.userservice.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeResource {
    final private LikeService likeService;
    final private UserService userService;
    final private PostService postService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> like(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String postId= data.get("postId");

        AppUser user = userService.getUser(username);
        String userId = user.getId();

        Likes like = new Likes(
            UUID.randomUUID().toString(),
            userId,
            postId
        );

        Post post = postService.getPostById(postId);
        post.setLike_count(post.getLike_count() + 1);

        return new ResponseEntity<>(likeService.like(like), HttpStatus.OK);
    }

    @Transactional
    @PostMapping(value = "/unlike", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> unlike(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String postId= data.get("postId");

        AppUser user = userService.getUser(username);
        String userId = user.getId();

        likeService.unlike(userId, postId);
        Post post = postService.getPostById(postId);

        post.setLike_count(post.getLike_count() - 1);

        Map<String, String> response = new HashMap<>();
        response.put("message", "unlike post successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    
    }

    @PostMapping("/isLiked")
    public boolean isFollowed(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String postId = data.get("postId");
        AppUser user = userService.getUser(username);
        String userId = user.getId();
        
        return likeService.isLike(userId, postId);
    }
}
