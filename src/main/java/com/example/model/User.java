package com.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "userId", nullable = false)
  private Long userId;

  @Getter
  @Setter
  @NotNull(message = "Name is required")
  @Column(name = "UserName", nullable = false)
  private String name;

  @Getter
  @Setter
  @NotNull(message = "Password is required")
  @Column(name = "UserPassword", nullable = false)
  private String password;

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  @Getter
  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<Category> categories = new ArrayList<>();


  public void addCategory(Category category) {
    categories.add(category);
  }
}
