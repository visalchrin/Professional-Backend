package io.getarrays.userservice.api;
import java.util.Collections;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.getarrays.userservice.domain.AppUser;
import io.getarrays.userservice.domain.News;
import io.getarrays.userservice.respository.NewsRepository;
import io.getarrays.userservice.respository.UserRepository;
import io.getarrays.userservice.service.NewsService;
import io.getarrays.userservice.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsResource {
    final private NewsService newsService;
    final private UserRepository userRepository;
    final private NewsRepository newsRepository;
    final private UserService userService;

    @GetMapping("/getAllNews")
    public ResponseEntity<List<News>> getAllNews() {
        List<News> ls = newsService.getAllNews();
        Collections.reverse(ls);
        return ResponseEntity.ok().body(ls);
    }

    @PostMapping("/getAllNewsByUsername")
    public ResponseEntity<List<News>> getAllNewsByUsername(@RequestBody Map<String, String> data) {
        String username = data.get("username");
        AppUser user = this.userService.getUser(username);
        List<News> ls = newsService.getAllNewsByUserId(user.getId());
        Collections.reverse(ls);
        return ResponseEntity.ok().body(ls);
    }

    @GetMapping("/article/popularity")
    public ResponseEntity<List<News>> getPopularArticles() {
        List<News> ls = newsService.getPopularArticles();
        return ResponseEntity.ok().body(ls);
    }

    @GetMapping("/article")
    public ResponseEntity<News> getNewsById(@RequestParam String Id) {
        return ResponseEntity.ok().body(newsService.getNewsById(Id));
    }


    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteNewsById(@RequestBody Map<String, String> data) {
        
        String id = data.get("id");
        this.newsRepository.deleteById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "delete news successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> createNews(@RequestBody Map<String, String> data) {
        
        String username = data.get("username");
        String title = data.get("title");
        String content = data.get("content");
        String image = data.get("image");
        AppUser u = userRepository.findByUsername(username);

        News news = new News(
            UUID.randomUUID().toString(),
            title,
            content,
            image,
            new Date(),
            u.getId()
        );

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("api/news/create").toUriString());
        return ResponseEntity.created(uri).body(newsService.createNews(news));
    }
}
