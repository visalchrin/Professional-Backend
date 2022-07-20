package io.getarrays.userservice.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.Comment;
import io.getarrays.userservice.service.CommentService;
import io.getarrays.userservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentResource {
    final private CommentService commentService;
    final private UserService userService;


    @GetMapping("/getCommentByPostId")
    public ResponseEntity<List<CommentResponse>> getCommentByPostId(@RequestParam String Id) {

        List<Comment> comments = commentService.getAllCommentsByPostId(Id);

        List<CommentResponse> ls = new ArrayList<>();

        comments.forEach((e) -> {
            AppUser user = userService.getUserById(e.getUserId());
            CommentResponse response = new CommentResponse(
                e.getId(), 
                e.getUserId(), 
                e.getPostId(), 
                e.getComment(), 
                e.getCreatedDate(), 
                user.getProfile(), 
                user.getFullname()
            );
            ls.add(response);
        });
        
        
        
        return ResponseEntity.ok().body(ls);
    }


    @PostMapping("/count")
    public long countFollower(@RequestBody Map<String, String> data) {
        String postId = data.get("postId");
        return commentService.getNumberOfComment(postId);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addComment(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String postId = data.get("postId");
        String comment = data.get("comment");

        AppUser user = userService.getUser(username);
        String userId = user.getId();

        Comment com = new Comment(
            UUID.randomUUID().toString(), 
            userId, 
            postId, 
            comment, 
            new Date()
        );

        Comment c = commentService.createComment(com);
        AppUser u = userService.getUserById(c.getUserId());
        CommentResponse response = new CommentResponse(
            c.getId(), 
            c.getUserId(), 
            c.getPostId(), 
            c.getComment(), 
            c.getCreatedDate(), 
            u.getProfile(), 
            u.getFullname());
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class CommentResponse {
    private String id;
    private String userId;
    private String postId;
    private String comment;
    private Date createdDate;
    private String profile;
    private String fullname;
}