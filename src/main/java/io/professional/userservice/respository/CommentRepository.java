package io.professional.userservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, String> {
    
    List<Comment> findByPostIdOrderByCreatedDateDesc(String postId);
    long countByPostId(String postId);
    //List<Post> findByOwnerIdOrderByCreatedDateDesc(String ownerId);

}
