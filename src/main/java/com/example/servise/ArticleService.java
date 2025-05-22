package com.example.servise;

import com.example.model.*;
import com.example.repository.ArticleRepository;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final UserRepository userRepository;
  private final KafkaProducerService kafkaProducerService;

  @Async
  @Transactional
  public CompletableFuture<List<Article>> getArticles(Long userId) throws JsonProcessingException {
    log.info("Getting articles for user with id {}", userId);
    List<Article> articles = new ArrayList<>();
    articles.add(new Article("Article 1", "Content 1"));

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.SELECT.name())
        .eventDetails("Получены категории пользователя для нахождения статей")
        .build());

    log.info("Articles found: {}", articles);
    return CompletableFuture.completedFuture(articles);
  }
}