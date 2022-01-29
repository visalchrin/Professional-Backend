package io.getarrays.userservice.service;

import java.util.List;

import io.getarrays.userservice.domain.Comment;

public interface CommentService {
    List<Comment> getAllCommentsByPostId(String postId);
    long getNumberOfComment(String postId);
    Comment createComment(Comment comment);
}
