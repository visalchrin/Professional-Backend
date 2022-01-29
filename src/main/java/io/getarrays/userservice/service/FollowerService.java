package io.getarrays.userservice.service;

import java.util.List;

import io.getarrays.userservice.domain.Follower;

public interface FollowerService {
    
    Follower follow(Follower follower);
    long getNumberOfFollower(String userId);
    List<Follower> getAllFollower(String userId);
    boolean isFollowed(String userId, String followerId);
    void deleteFollower(String userId, String followerId);
}
