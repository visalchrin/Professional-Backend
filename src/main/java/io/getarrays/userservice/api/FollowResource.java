package io.getarrays.userservice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.Follower;
import io.getarrays.userservice.domain.Following;
import io.getarrays.userservice.respository.FollowerRepository;
import io.getarrays.userservice.service.FollowerService;
import io.getarrays.userservice.service.FollowingService;
import io.getarrays.userservice.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/follow")
@RequiredArgsConstructor
public class FollowResource {

    final FollowingService followingService;
    final FollowerService followerService;
    final UserService userService;
    final FollowerRepository followerRepository;

    @PostMapping("/followers/count")
    public long countFollower(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        AppUser user = userService.getUser(username);
        String userId = user.getId();
        return this.followerRepository.countByUserId(userId);
    }

    @PostMapping("/unfollow")
    public ResponseEntity<?> unfollow(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String follow = data.get("follow");

        AppUser user = userService.getUser(username);
        String userId = user.getId();
        user = userService.getUser(follow);
        String followId = user.getId();

        followerService.deleteFollower(followId, userId);
        followingService.deleteFollowing(userId, followId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "unfollow is completed");
        response.put("userId", userId.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    

    @PostMapping("/isFollowed")
    public boolean isFollowed(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String follower = data.get("follow");
        AppUser user = userService.getUser(username);
        String userId = user.getId();
        user = userService.getUser(follower);
        String followerId = user.getId();
        return followerService.isFollowed(userId, followerId);
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> follow(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        String follow = data.get("follow");

        AppUser user = userService.getUser(username);
        String userId = user.getId();

        user = userService.getUser(follow);
        String followId = user.getId();

        Follower follower = new Follower(
            UUID.randomUUID().toString(),
            followId,
            userId
        );

        Following following = new Following(
            UUID.randomUUID().toString(),
            userId,
            followId
        );
        followerService.follow(follower);
        return new ResponseEntity<>(followingService.following(following), HttpStatus.OK);
    }
}
