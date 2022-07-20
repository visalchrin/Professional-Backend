package io.professional.userservice.service;

import io.professional.userservice.domain.Likes;

public interface LikeService {
    Likes like(Likes like);
    void unlike(String userId, String postId);
    boolean isLike(String userId, String postId);
}
