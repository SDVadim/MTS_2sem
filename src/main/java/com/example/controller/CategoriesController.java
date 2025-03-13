package com.example.controller;

import com.example.api.CategoryApi;
import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import com.example.servise.CategoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RateLimiter(name = "rateLimiter")
@CircuitBreaker(name = "circuitBreaker")
@RestController
public class CategoriesController implements CategoryApi {

  @Autowired
  private CategoryService categoryService;

  @Override
  public ResponseEntity<Category> updateCategory(CategoryData categoryData, Long categoryId) {
    return null;
  }

  @Override
  public ResponseEntity<CategoryId> createCategory(CategoryData categoryData, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.createCategory(categoryData, userId));
  }

  @Override
  public ResponseEntity<List<Category>> getAllCategories(Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAllCategories(userId));
  }

  @Override
  public ResponseEntity<Category> getCategory(Long categoryId) {
    return ResponseEntity.status(HttpStatus.OK).body(categoryService.getCategory(categoryId));
  }

  @Override
  public ResponseEntity<Void> deleteCategory(Long categoryId) {
    try {
      categoryService.deleteCategory(categoryId);
      return ResponseEntity.noContent().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }

  @Override
  public ResponseEntity<Void> deleteUser (Long userId) {
    categoryService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }
}