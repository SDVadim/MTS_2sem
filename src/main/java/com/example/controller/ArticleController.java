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
  private ArticleServise articleServise;


  @GetMapping("/{userId}")
  public Map<Article, Category> getArticles(@PathVariable Long userId) {
    return articleServise.getArticles(userId);
  }
}

/**
public class ArticleController implements {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);
  private final Service service;
  private final ArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticleController(Service service, ArticleService articleService, ObjectMapper objectMapper) {
    this.service = service;
    this.articleService = articleService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    getArticle();
  }

  private void getArticle() {
    service.get("/articles",
        (Request request, Response response) -> {
          String body = request.body();
          ArticleRequest articleRequest = objectMapper.readValue(body, ArticleRequest.class);
          try {
            // тут тоже нужен userId сделано
            Map<Article, Category> articles = articleService.getArticles(new UserId(articleRequest.userId()));
            response.status(200);
            ArrayList<ArrayList<String>> pairs = new ArrayList<>();
            for (Article article : articles.keySet()) {
              ArrayList<String> pairin = new ArrayList<>();
              pairin.add(article.getName());
              pairin.add(article.getUrl());
              pairin.add(articles.get(article).name());
              pairs.add(pairin);
            }
            // название article категория и ссылка
            return objectMapper.writeValueAsString(pairs);
          } catch (Exception e) {
            response.status(500);
            if (LOG.isErrorEnabled()) {
              LOG.error("Error when getting articles");
            }
            return objectMapper.writeValueAsString("Error when getting articles");
          }
        });
  }
}
 **/