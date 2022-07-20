package io.professional.userservice.service.Implement;

import java.util.List;
import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Follower;
import io.professional.userservice.respository.FollowerRepository;
import io.professional.userservice.service.FollowerService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService{

    final FollowerRepository followerRepository;

    @Override
    public Follower follow(Follower follower) {
        return followerRepository.save(follower);
    }

    @Override
    public long getNumberOfFollower(String userId) {
        return this.followerRepository.countByUserId(userId);
    }

    @Override
    public List<Follower> getAllFollower(String userId) {
        return followerRepository.findAllByUserId(userId);
    }

    @Override
    public boolean isFollowed(String userId, String followerId) {
        // Follower object = new Follower();
        // object.setUserId(userId);
        // object.setFollowerId(followerId);
        // return followerRepository.exists(Example.of(object));
        return followerRepository.existsByUserIdAndFollowerId(userId, followerId);
    }

    @Override
    public void deleteFollower(String userId, String followerId) {
        Follower follower = followerRepository.findByUserIdAndFollowerId(userId, followerId);
        followerRepository.delete(follower);
        
    }
    
}
