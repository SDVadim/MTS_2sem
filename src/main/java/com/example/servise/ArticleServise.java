package com.example.servise;

import com.example.model.Article;
import com.example.model.ArticleId;
import com.example.model.Category;
import com.example.repoitory.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@AllArgsConstructor
public class ArticleServise {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleServise.class);
  ArticleRepository articleRepository;

  public Map<Article, Category> getArticles(Long userId) {
    LOG.info("Получили список статей и категорий");
    return null;
  }

  public List<Article> findAll() {
    return articleRepository.findAll();
  }

  public ArticleId create(String name, String url) {
    return articleRepository.create(name, url);
  }

  public void delete(ArticleId articleId) {
    articleRepository.delete(articleId);
  }
}
