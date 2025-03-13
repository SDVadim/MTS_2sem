package com.example.controller;

import com.example.api.ArticleApi;
import com.example.model.Article;
import com.example.model.Category;
import com.example.servise.ArticleService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RateLimiter(name = "rateLimiter")
@RestController
public class ArticlesController implements ArticleApi {
  private final ArticleService articleService;

  public ArticlesController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @Override
  public CompletableFuture<ResponseEntity<List<Article>>> getArticles(Long userId) {
    return articleService.getArticles(userId).thenApply(
        article -> ResponseEntity.status(HttpStatus.OK).body(article));
  }
}