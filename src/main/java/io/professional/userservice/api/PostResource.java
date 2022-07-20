package io.professional.userservice.api;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.professional.userservice.domain.AppUser;
import io.professional.userservice.domain.Following;
import io.professional.userservice.domain.Post;
import io.professional.userservice.respository.PostRepository;
import io.professional.userservice.service.CommentService;
import io.professional.userservice.service.FollowingService;
import io.professional.userservice.service.LikeService;
import io.professional.userservice.service.PostService;
import io.professional.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostResource {
    final PostService postService;
    final UserService userService;
    final LikeService likeService;
    final PostRepository postRepository;
    final FollowingService followingService;
    final CommentService commentService;

    @GetMapping("/getPostFromFollowing")
    public ResponseEntity<List<PostsResponse>> getPostsFromFollowingUsers (@RequestParam("username") String username) {
        AppUser user = this.userService.getUser(username);
        String userId = user.getId();

        List<String> followingList = new ArrayList<>();
        List<Following> followings = followingService.getAllFollowing(userId);
        
        followings.forEach(e -> {
            followingList.add(e.getFollowingId());
        });

        List<Post> ls = postService.getPostsFromFollowingUser(followingList);
        List<PostsResponse> postList =  new ArrayList<PostsResponse>();

        ls.forEach((e) -> {
            AppUser postOwner = this.userService.getUser(e.getOwnerUsername());
            PostsResponse p = new PostsResponse(
                e.getId(),
                e.getContent(),
                e.getOwnerId(),
                e.getCreatedDate(),
                e.getOwnerName(),
                e.getOwnerUsername(),
                e.getLike_count(),
                likeService.isLike( userId,e.getId()),
                commentService.getNumberOfComment(e.getId()),
                postOwner.getProfile()
            );
            postList.add(p);
        });

        return ResponseEntity.ok().body(postList);
    }


    @GetMapping("/getAllPostsByOwnerId")
    public ResponseEntity<List<PostsResponse>> getAllPostsByOwnerId(@RequestParam("username") String username) {
        
        AppUser user = this.userService.getUser(username);
        String ownerId = user.getId();
        List<Post> ls = postService.getAllPostsByOwnerId(ownerId);

        List<PostsResponse> list =  new ArrayList<PostsResponse>();

        ls.forEach((e) -> {
            AppUser postOwner = this.userService.getUser(e.getOwnerUsername());
            PostsResponse p = new PostsResponse(
                e.getId(),
                e.getContent(),
                e.getOwnerId(),
                e.getCreatedDate(),
                e.getOwnerName(),
                e.getOwnerUsername(),
                e.getLike_count(),
                likeService.isLike( ownerId,e.getId()),
                commentService.getNumberOfComment(e.getId()),
                postOwner.getProfile()
            );
            list.add(p);
        });

        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/getAllPosts")
    public ResponseEntity<List<Post>> getAllPosts() {
        
        return ResponseEntity.ok().body(postRepository.findAll());
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostsResponse> createNews(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String content = data.get("content");
        AppUser user = userService.getUser(username); 

        Post post = new Post(
          UUID.randomUUID().toString(),
          content, 
          user.getId(), 
          new Date(),
          user.getFullname(),
          username,
          0
        );
        Post p = postService.createPost(post);

        PostsResponse postsResponse = new PostsResponse(
                p.getId(),
                p.getContent(),
                p.getOwnerId(),
                p.getCreatedDate(),
                p.getOwnerName(),
                p.getOwnerUsername(),
                p.getLike_count(),
                likeService.isLike( user.getId(),p.getId()),
                commentService.getNumberOfComment(p.getId()),
                user.getProfile()
            );

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/save/create").toUriString());
        return ResponseEntity.created(uri).body(postsResponse);
    }

    @GetMapping("/getPostById")
    public ResponseEntity<Post> getNewsById(@RequestParam String Id) {
        return ResponseEntity.ok().body(postService.getPostById(Id));
    }


}

@Data
@NoArgsConstructor
@AllArgsConstructor
class PostsResponse {
    private String id;
    private String content;
    private String ownerId;
    private Date createdDate;
    private String ownerName;
    private String ownerUsername;
    private Integer like_count;
    private Boolean isLiked;
    private long comment_count;
    private String profile;
}