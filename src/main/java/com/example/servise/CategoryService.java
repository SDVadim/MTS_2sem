package com.example.servise;

import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import com.example.model.UserId;
import com.example.repoitory.DbCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
  private final DbCategoryRepository categoryRepository;

  public CategoryId createCategory(CategoryData categoryData, Long userId) {
    return categoryRepository.createCategory(categoryData.getName(), new UserId(userId));
  }

  public List<Category> findAllCategories(Long userId) {
    return categoryRepository.findAllCategories(new UserId(userId));
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
