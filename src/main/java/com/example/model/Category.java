package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category{

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoryId", nullable = false)
  Long categoryId;

  @Getter
  @Setter
  @NotNull(message = "Name is required")
  @Column(name = "CategoryName", nullable = false)
  String name;

  public Category(String name, User user) {
    this.name = name;
    this.user = user;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  @NotNull(message = "Category user has to be filled")
  private User user;

  @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
  private final List<Article> articles = new ArrayList<>();

}
