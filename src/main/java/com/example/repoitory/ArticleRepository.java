package com.example.repoitory;

import com.example.model.Article;
import com.example.model.ArticleId;

import java.util.List;

public interface ArticleRepository {

  ArticleId generateId();

  List<Article> findAll();

  ArticleId create(String name, String url);

  void delete(ArticleId articleId);
}
