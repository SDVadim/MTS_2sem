package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity
@Table(name = "articles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "articleId", nullable = false)
  private Long articleId;

  @Getter
  @NotNull(message = "Name is required")
  @Column(name = "ArticleTitle", nullable = false)
  private String name;

  @Getter
  @NotNull(message = "URL is required")
  @Column(name = "ArticleUrl", nullable = false)
  private String url;

  public Article(String title, String url) {
    this.name = title;
    this.url = url;
  }

  @Getter
  @NotNull(message = "Category is required")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Category", nullable = false)
  private Category category;
}
