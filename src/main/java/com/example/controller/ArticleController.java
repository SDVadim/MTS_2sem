package com.example.controller;


import com.example.model.Article;
import com.example.model.Category;
import com.example.model.UserId;
import com.example.servise.ArticleServise;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class ArticleController {
  private final ArticleServise articleServise;

  @GetMapping("/{userId}")
  public Map<Article, Category> getArticles(@PathVariable Long userId) {
    return articleServise.getArticles(userId);
  }
}
