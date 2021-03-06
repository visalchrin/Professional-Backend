package io.professional.userservice.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Likes;

public interface LikeRepository  extends JpaRepository<Likes, String> {
    
    boolean existsByUserIdAndPostId(String userId, String postId);
    Likes findByUserIdAndPostId (String userId, String postId);
}
