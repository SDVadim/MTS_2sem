package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category{

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "categoryId")
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
