package com.example.repoitory;

import com.example.model.Article;
import com.example.model.ArticleId;
import com.example.model.Category;
import com.example.model.UserId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DbArticleRepository implements ArticleRepository {
  private static final Logger LOG = LoggerFactory.getLogger(DbArticleRepository.class);
  private final List<Article> articleList = new ArrayList<>();
  private final AtomicLong id = new AtomicLong(0);

  private ArticleId generateId() {
    LOG.info("Создали ID для статьи");
    return new ArticleId(id.incrementAndGet());
  }

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WebClient webClient;

  public List<Article> findAllArticles() {
    String randomUrl = "https://www.random.org/integers";
    String response = restTemplate.getForObject(randomUrl, String.class);
    System.out.println("Ответ от RestTemplate: " + response);
    LOG.info("Получили все статьи. Всего статей: {}", articleList.size());
    return articleList;
  }

  public ArticleId createArticle(String name, String url) {
    Article article = Article.builder().name("JAVA").url("https://javarush.com").build();
    ArticleId articleId = generateId();
    article.setArticleId(articleId);
    LOG.info("Создали статью: {} с ID: {}", name, articleId);
    articleList.add(article);
    return articleId;
  }

  public Map<Article, Category> getArticles(UserId userId) {
    String randomUrl = "https://www.random.org/integers";
    String response = webClient.get()
        .uri(randomUrl)
        .retrieve()
        .bodyToMono(String.class)
        .block();

    System.out.println("Ответ от WebClient: " + response);
    return Map.of();
  }

  public void deleteArticle(ArticleId articleId) {
    for (Article article : articleList) {
      if (article.getArticleId().equals(articleId)) {
        articleList.remove(article);
        LOG.info("Удалили статью с ID: {}", articleId);
      }
    }
  }
}
