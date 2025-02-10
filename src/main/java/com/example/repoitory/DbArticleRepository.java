package com.example.repoitory;

import com.example.model.Article;
import com.example.model.ArticleId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DbArticleRepository implements ArticleRepository {
  private static final Logger LOG = LoggerFactory.getLogger(DbArticleRepository.class);
  private final List<Article> repository = new ArrayList<>();

  public ArticleId generateId() {
    LOG.info("Создали Id для статьи");
    return null;
  }

  public List<Article> findAll() {
    LOG.info("Получили все статьи");
    return repository;
  }

  public ArticleId create(String name, String url) {
    LOG.info("создали статью");
    repository.add(null);
    return null;
  }

  public void delete(ArticleId articleId){
    LOG.info("Удалили статью");
  }
}
