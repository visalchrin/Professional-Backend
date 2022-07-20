package io.professional.userservice.respository;

import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.professional.userservice.domain.News;

public interface NewsRepository extends JpaRepository<News, String> {
    News findByTitle (String title);

    List<News> findByUserId(String userId);
    
    @Query(nativeQuery = true, value = "SELECT * FROM News ORDER BY created_date DESC LIMIT 3;")
    List<News> getPopularArticles();
}
    
