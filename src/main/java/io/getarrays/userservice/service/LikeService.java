package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.Likes;

public interface LikeService {
    Likes like(Likes like);
    void unlike(String userId, String postId);
    boolean isLike(String userId, String postId);
}
