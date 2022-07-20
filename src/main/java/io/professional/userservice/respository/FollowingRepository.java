package io.getarrays.userservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userservice.domain.Following;

public interface FollowingRepository extends JpaRepository<Following, String>{
    List<Following> findAllByUserId(String userId);
    long countByUserId(String userId);
    Following findByUserIdAndFollowingId (String userId, String followingId);
}
