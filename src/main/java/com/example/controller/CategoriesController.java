package com.example.controller;

import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import com.example.servise.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {
  private final CategoryService categoryService;

  @PostMapping("/create")
  public CategoryId createCategory(@RequestBody CategoryData categoryData) {
    return categoryService.createCategory(categoryData.getName(), categoryData.getUserId());
  }

  @GetMapping("/user/{userId}")
  public List<Category> getAllCategories(@PathVariable Long userId) {
    return categoryService.findAll(userId);
  }

  @GetMapping("/{categoryId}")
  public Category getCategory(@PathVariable Long categoryId) {
    return categoryService.getCategory(categoryId);
  }

  @DeleteMapping("/delete/{categoryId}")
  public void deleteCategory(@PathVariable Long categoryId) {
    categoryService.deleteCategory(categoryId);
  }

  @DeleteMapping("/delete/user/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    categoryService.deleteUser(userId);
  }
}
