package io.professional.userservice.service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import io.professional.userservice.domain.Comment;
import io.professional.userservice.respository.CommentRepository;
import io.professional.userservice.service.CommentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    final private CommentRepository commentRepository;

    @Override
    public List<Comment> getAllCommentsByPostId(String postId) {
        return commentRepository.findByPostIdOrderByCreatedDateDesc(postId);
    }

    @Override
    public long getNumberOfComment(String postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }
    
}
