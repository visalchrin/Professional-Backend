package io.professional.userservice.service.Implement;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Following;
import io.professional.userservice.respository.FollowingRepository;
import io.professional.userservice.service.FollowingService;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class FollowingServiceImpl implements FollowingService{
    
    final FollowingRepository followingRepository;

    @Override
    public Following following(Following following) {
        return followingRepository.save(following);
    }

    @Override
    public long getNumberOfFollowing(String userId) {
        return followingRepository.countByUserId(userId);
    }

    @Override
    public List<Following> getAllFollowing(String userId) {
        return followingRepository.findAllByUserId(userId);
    }

    @Override
    public boolean isFollowing(String userId, String followingId) {
        Following object = new Following();
        object.setUserId(userId);
        object.setFollowingId(followingId);
        return followingRepository.exists(Example.of(object));
    }

    @Override
    public void deleteFollowing(String userId, String followingId) {

        Following following = followingRepository.findByUserIdAndFollowingId(userId, followingId);
        followingRepository.delete(following);
    }
}
