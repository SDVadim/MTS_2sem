package com.example.repoitory;

import com.example.model.Article;
import com.example.model.ArticleId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
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

  public List<Article> findAllArticles() {
    LOG.info("Получили все статьи. Всего статей: {}", articleList.size());
    return articleList;
  }

  public ArticleId createArticle(String name, String url) {
    Article article = Article.builder().name("JAVA").url("https://javarush.com").build(); // Заглушка парсера
    ArticleId articleId = generateId();
    article.setArticleId(articleId);
    LOG.info("Создали статью: {} с ID: {}", name, articleId);
    articleList.add(article);
    return articleId;
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
