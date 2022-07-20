package io.professional.userservice.service.Implement;

import java.util.List;

import org.springframework.stereotype.Service;

import io.professional.userservice.domain.News;
import io.professional.userservice.respository.NewsRepository;
import io.professional.userservice.service.NewsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewServiceImpl implements NewsService{

    private final NewsRepository newsRepo;

    @Override
    public News createNews(News news) {
        return newsRepo.save(news);
    }

    @Override
    public News getNewsById(String Id) {
        News news = newsRepo.getById(Id);
        
        return news;
    }

    @Override
    public List<News> getAllNews() {
        return newsRepo.findAll();
    }

    @Override
    public News getNewsByTitle(String title) {
        return newsRepo.findByTitle(title);
    }

    @Override
    public List<News> getPopularArticles() {
        
        return newsRepo.getPopularArticles();
    }

    @Override
    public List<News> getAllNewsByUserId(String userId) {
        return this.newsRepo.findByUserId(userId);
    }
    
}
