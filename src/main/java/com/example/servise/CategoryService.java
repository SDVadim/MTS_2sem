package com.example.servise;

import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import com.example.model.UserId;
import com.example.repoitory.DbCategoryRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
  private final DbCategoryRepository categoryRepository;
  private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

  @CacheEvict(value = "categories", allEntries = true)
  public CategoryId createCategory(CategoryData categoryData, Long userId) {
    LOG.info("Creating Category with name {}", categoryData.getName());
    return categoryRepository.createCategory(categoryData.getName(), new UserId(userId));
  }

  @Cacheable(value = "categories", key = "#userId")
  public List<Category> findAllCategories(Long userId) {
    LOG.info("Finding all categories");
    return categoryRepository.findAllCategories(new UserId(userId));
  }

  @Cacheable(value = "category", key = "#categoryId")
  public Category getCategory(Long categoryId) {
    LOG.info("findById({})", categoryId);
    return categoryRepository.getCategory(new CategoryId(categoryId));
  }

  @CacheEvict(value = "category", key = "#categoryId")
  public void deleteCategory(Long categoryId) {
    LOG.info("Deleting Category with id {}", categoryId);
    categoryRepository.deleteCategory(new CategoryId(categoryId));
  }

  @CacheEvict(value = "categories", key = "#userId")
  public void deleteUser(Long userId) {
    LOG.info("Deleting all categories for user with id {}", userId);
    categoryRepository.deleteUser(new UserId(userId));
  }
}