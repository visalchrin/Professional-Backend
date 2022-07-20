package io.getarrays.userservice.respository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import io.getarrays.userservice.domain.Follower;

public interface FollowerRepository extends JpaRepository<Follower, String> {
    List<Follower> findAllByUserId(String userId);
    long countByUserId(String userId);

    boolean existsByUserIdAndFollowerId(String userId, String followerId);

    Follower findByUserIdAndFollowerId (String userId, String followerId);
    //Follower findByUserIdAndFollowerId(@Param("userId")String userId,@Param("followerId") String followerId);
}
