package com.example.servise;

import com.example.model.Action;
import com.example.model.Article;
import com.example.model.DtoMessage;
import com.example.repository.ArticleRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@AllArgsConstructor
public class ArticleService {
  private final ArticleRepository articleRepository;
  private final KafkaProducerService kafkaProducerService;

  @Async
  @Transactional
  public CompletableFuture<List<Article>> getArticles(Long userId) throws JsonProcessingException {
    log.info("Getting articles for user with id {}", userId);
    List<Article> articles = articleRepository.findById(userId).stream().toList();

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.SELECT.name())
        .eventDetails("Найдены все статьи пользователя")
        .build());

    log.info("Articles found: {}", articles);
    return CompletableFuture.completedFuture(articles);
  }
}