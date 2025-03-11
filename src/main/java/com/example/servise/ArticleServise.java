package com.example.servise;

import com.example.model.Article;
import com.example.model.ArticleId;
import com.example.model.Category;
import com.example.repoitory.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ArticleServise {
  ArticleRepository articleRepository;

  public Map<Article, Category> getArticles(Long userId) {
    return null;
  }

  public List<Article> findAll() {
    return articleRepository.findAllArticles();
  }

  public ArticleId create(String name, String url) {
    return articleRepository.createArticle(name, url);
  }

  public void delete(Long articleId) {
    articleRepository.deleteArticle(new ArticleId(articleId));
  }
}
