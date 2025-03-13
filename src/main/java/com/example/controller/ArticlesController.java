package com.example.controller;

import com.example.api.ArticleApi;
import com.example.model.Article;
import com.example.model.Category;
import com.example.model.UserId;
import com.example.servise.ArticleService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
  public CompletableFuture<ResponseEntity<Map<Article, Category>>> getArticles(Long userId) {
    return articleService.getArticles(new UserId(userId)).thenApply(articles -> ResponseEntity.status(HttpStatus.OK).body(articles));
  }
}