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
  private Long articleId;

  @Getter
  @NotNull(message = "Name is required")
  private String name;

  @Getter
  @NotNull(message = "URL is required")
  private String url;

  @Getter
  @NotNull(message = "Category is required")
  private CategoryId categoryId;
}
