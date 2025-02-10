package com.example.servise;

import com.example.model.Category;
import com.example.model.CategoryId;
import com.example.model.UserId;
import com.example.repoitory.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;

  public CategoryId createCategory(String name, Long userId) {
    return categoryRepository.createCategory(name, new UserId(userId));
  }

  public List<Category> findAll(Long userId) {
    return categoryRepository.findAll(new UserId(userId));
  }

  public Category getCategory(Long categoryId) {
    return categoryRepository.getCategory(new CategoryId(categoryId));
  }

  public void deleteCategory(Long categoryId) {
    categoryRepository.deleteCategory(new CategoryId(categoryId));
  }

  public void deleteUser(Long userId) {
    categoryRepository.deleteUser(new UserId(userId));
  }
}
