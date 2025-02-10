package com.example.repoitory;

import com.example.model.Category;
import com.example.model.CategoryId;
import com.example.model.UserId;
import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;


@Repository
@AllArgsConstructor
public class CategoryRepository {
  private static final Logger LOG = LoggerFactory.getLogger(CategoryRepository.class);
  private List<Category> categoryList;
  private final AtomicLong id = new AtomicLong(0);

  private CategoryId generateId() {
    return new CategoryId(id.incrementAndGet());
  }

  public CategoryId createCategory(String name, UserId userId) {
    CategoryId categoryId = generateId();
    Category category = Category.builder().categoryId(categoryId).userId(userId).name(name).build();
    LOG.info("Создали категорию");
    categoryList.add(category);
    return categoryId;
  }

  public List<Category> findAll(UserId userId) {
    List<Category> list = new ArrayList<>();
    for (Category category : categoryList) {
      if (category.getUserId().equals(userId)) {
        list.add(category);
        LOG.info("Нашли все категории пользователя: {}", userId);
      }
    }
    return list;
  }

  public Category getCategory(CategoryId categoryId) {
    for (Category category : categoryList) {
      if (category.getCategoryId().equals(categoryId)) {
        LOG.info("Получили категорию по id: {}", categoryId);
        return category;
      }
    }
    LOG.info("Категория с id: {} не найдена", categoryId);
    return null;
  }

  public void deleteCategory(CategoryId categoryId) {
    for (Category category : categoryList) {
      if (category.getCategoryId().equals(categoryId)) {
        categoryList.remove(category);
        LOG.info("Категория с id: {} удалена", categoryId);
      }
    }
    return;
  }

  public void deleteUser(UserId userId) {
    for (Category category : categoryList) {
      if (category.getUserId().equals(userId)) {
        categoryList.remove(category);

      }
    }
    LOG.info("Все категории пользователя {} удаены", userId);
  }


}
