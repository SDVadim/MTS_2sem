package com.example.servise;

import com.example.model.Article;
import com.example.model.Category;
import com.example.model.UserId;
import com.example.repoitory.DbArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class ArticleService {
  public DbArticleRepository articleRepository;

  public ArticleService(DbArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  @Async
  public CompletableFuture<Map<Article, Category>> getArticles(UserId userId) {
    Map<Article, Category> articles = articleRepository.getArticles(userId);
    return CompletableFuture.completedFuture(articles);
  }
}