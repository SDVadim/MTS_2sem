package com.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Article {
  private String name;
  private ArticleId articleId;
  private String url;
  private CategoryId categoryId;
}
