package com.example.servise;

import com.example.model.Article;
import com.example.repoitory.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;

  @Async
  @Transactional
  public CompletableFuture<List<Article>> getArticles(Long userId) {
    log.info("Getting articles for user with id {}", userId);

    List<Article> articles = articleRepository.findAll();
    log.info("Articles found: {}", articles);

    return CompletableFuture.completedFuture(articles);
  }
}