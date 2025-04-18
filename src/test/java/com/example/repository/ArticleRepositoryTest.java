package com.example.repository;

import com.example.model.*;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
public class ArticleRepositoryTest {
  @Autowired
  ArticleRepository articleRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @Test
  void saveArticleAndFindThemTest() {
    Article article = new Article("Test Article", "http://example.com");

    User user = new User("user_1234", "1234");
    userRepository.save(user);
    Category category = new Category("Test Category", user);
    categoryRepository.save(category);

    article.setCategory(category);
    articleRepository.save(article);

    Optional<Article> retrievedArticle = articleRepository.findById(article.getArticleId());

    assertTrue(retrievedArticle.isPresent());
    assertEquals("Test Article", retrievedArticle.get().getName());
    assertEquals("http://example.com", retrievedArticle.get().getUrl());
  }
}
