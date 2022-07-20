package io.professional.userservice.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.professional.userservice.domain.Post;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findByOwnerId (String ownerId);
    List<Post> findByOwnerIdInOrderByCreatedDateDesc(List<String> ownerId);
    List<Post> findByOwnerIdOrderByCreatedDateDesc(String ownerId);
}
