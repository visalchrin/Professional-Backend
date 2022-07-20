package io.getarrays.userservice.service;

import java.util.List;

import io.getarrays.userservice.domain.News;

public interface NewsService {

    News createNews(News news);
    News getNewsById(String Id);
    News getNewsByTitle(String title);
    List<News> getPopularArticles();
    List<News> getAllNews();
    List<News> getAllNewsByUserId(String userId);
}
