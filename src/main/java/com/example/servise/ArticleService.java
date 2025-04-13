package com.example.servise;

import com.example.model.*;
import com.example.repository.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;

  @Async
  @Transactional
  public CompletableFuture<List<Article>> getArticles(Long userId) {
    log.info("Getting articles for user with id {}", userId);
    List<Article> articles = new ArrayList<>();
    articles.add(new Article("Article 1", "Content 1"));
    log.info("Articles found: {}", articles);
    return CompletableFuture.completedFuture(articles);
  }
}