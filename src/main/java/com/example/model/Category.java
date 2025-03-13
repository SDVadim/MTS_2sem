package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category{

  @Getter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long categoryId;

  @Getter
  @NotNull(message = "Name is required")
  String name;

  @Getter
  @NotNull(message = "User ID is required")
  Long userId;

  public Category(String name, Long userId) {
    this.name = name;
    this.userId = userId;
  }
}
