package com.example.controller;

import com.example.api.ArticleApi;
import com.example.model.Article;
import com.example.model.Category;
import com.example.servise.ArticleServise;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class ArticlesController implements ArticleApi{
  private ArticleServise articleServise;

  @Override
  public ResponseEntity<Map<Article, Category>> getArticles(Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(articleServise.getArticles(userId));
  }
}
