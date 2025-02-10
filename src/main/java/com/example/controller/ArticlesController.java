package com.example.controller;


import com.example.model.Article;
import com.example.model.ArticleId;
import com.example.model.Category;
import com.example.servise.ArticleServise;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/articles")
@AllArgsConstructor
public class ArticlesController {
  private final ArticleServise articleServise;

  @GetMapping("/{userId}")
  public Map<Article, Category> getArticles(@PathVariable Long userId) {
    return articleServise.getArticles(userId);
  }
}
