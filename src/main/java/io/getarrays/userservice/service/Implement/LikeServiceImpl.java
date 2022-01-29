package io.getarrays.userservice.service.Implement;

import org.springframework.stereotype.Service;

import io.getarrays.userservice.domain.Likes;
import io.getarrays.userservice.respository.LikeRepository;
import io.getarrays.userservice.service.LikeService;
import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    final LikeRepository likeRepository;

    @Override
    public Likes like(Likes like) {
        return likeRepository.save(like);
    }

    @Override
    public void unlike(String userId, String postId) {
        Likes like = likeRepository.findByUserIdAndPostId(userId, postId);
        likeRepository.delete(like);
    }

    @Override
    public boolean isLike(String userId, String postId) {
        return likeRepository.existsByUserIdAndPostId(userId, postId);
    }
    
}
