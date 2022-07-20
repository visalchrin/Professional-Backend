package io.getarrays.userservice.service;

import java.util.List;

import io.getarrays.userservice.domain.Following;

public interface FollowingService {
    Following following(Following following);
    long getNumberOfFollowing(String userId);
    List<Following> getAllFollowing(String userId);
    boolean isFollowing(String userId, String followingId);
    void deleteFollowing(String userId, String followingId);
}
