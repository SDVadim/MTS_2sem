package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;

  @NotNull(message = "Name is required")
  private String name;

  @NotNull(message = "Email is required")
  private String password;

  @Getter
  @OneToMany(mappedBy = "userId", fetch = FetchType.LAZY)
  private final List<Category> categories = new ArrayList<>();

  public void addCategory(Category category) {
    categories.add(category);
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }
}
