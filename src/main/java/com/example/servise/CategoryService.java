package com.example.servise;

import com.example.model.Action;
import com.example.model.Category;
import com.example.model.DtoMessage;
import com.example.model.User;
import com.example.model.request.CategoryData;
import com.example.repository.CategoryRepository;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;
  private final KafkaProducerService kafkaProducerService;

  @Transactional
  @CacheEvict(value = "categories", allEntries = true)
  public Category createCategory(CategoryData categoryData, Long userId) throws JsonProcessingException {
    log.info("Creating Category with name {}", categoryData.getName());

    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    log.info("User found: {}, to create category", user);

    Category category = new Category(categoryData.getName(), user);
    log.info("Category created: {}", category);

    categoryRepository.save(category);
    log.info("Category saved: {}", category);

    user.addCategory(category);
    log.info("Category added to user: {}", user);

    userRepository.save(user);
    log.info("User saved: {}", user);

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.INSERT.name())
        .eventDetails("Создана категория для пользователя")
        .build());

    return category;
  }


  @Transactional
  @Cacheable(value = "categories", key = "#userId")
  public List<Category> findAllCategories(Long userId) throws JsonProcessingException {
    log.info("Finding all categories");

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.SELECT.name())
        .eventDetails("Получены все категории пользователя")
        .build());

    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    log.info("User found: {}", user);

    List<Category> categories = new ArrayList<>();
    for (Category category : user.getCategories()) {
      categories.add(category);
    }
    log.info("Categories found: {}", categories);

    return categories;
  }

  @Transactional
  @Cacheable(value = "category", key = "#categoryId")
  public Category findCategoryById(Long categoryId) {
    log.info("findById({})", categoryId);

    Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));
    log.info("Category found: {}", category);

    return category;
  }

  @Transactional
  @CacheEvict(value = "category", key = "#categoryId")
  public void deleteCategory(Long categoryId) {
    log.info("Deleting Category with id {}", categoryId);
    if (!categoryRepository.existsById(categoryId)) {
      log.error("Category not found");
      throw new RuntimeException("Category not found");
    }
    categoryRepository.deleteById(categoryId);
    log.info("Category deleted");
  }

  @Transactional
  @CacheEvict(value = "categories", key = "#userId")
  public void deleteUser(Long userId) throws JsonProcessingException {
    log.info("Deleting all categories for user with id {}", userId);

    User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    log.info("User found: {} to delete ", user);

    for (Category category : user.getCategories()) {
      categoryRepository.delete(category);
      log.info("Category deleted: {}", category);
    }

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.SELECT.name())
        .eventDetails("Получены все категории пользователя")
        .build());
    log.info("Categories deleted");
  }
}