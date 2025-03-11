package com.example.repoitory;

import com.example.model.Article;
import com.example.model.ArticleId;

import java.util.List;

public interface ArticleRepository {
  List<Article> findAllArticles();

  ArticleId createArticle(String name, String url);

  void deleteArticle(ArticleId articleId);
}
