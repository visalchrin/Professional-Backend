package io.getarrays.userservice.respository;

import java.util.List;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.getarrays.userservice.domain.News;

public interface NewsRepository extends JpaRepository<News, String> {
    News findByTitle (String title);
    
    @Query(nativeQuery = true, value = "SELECT * FROM News ORDER BY created_date DESC LIMIT 3;")
    List<News> getPopularArticles();
}
    
