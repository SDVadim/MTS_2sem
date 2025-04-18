package com.example.repository;

import com.example.model.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.extern.slf4j.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.test.context.ActiveProfiles;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
public class CategoryRepositoryTest {
  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  UserRepository userRepository;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");

  @Test
  void saveCategoryTest() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    Category category = new Category("Test Category", user);
    categoryRepository.save(category);

    Optional<Category> retrievedCategory = categoryRepository.findById(category.getCategoryId());
    assertTrue(retrievedCategory.isPresent());
    assertEquals("Test Category", retrievedCategory.get().getName());
  }

  @Test
  void deleteCategoryTest() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    Category category = new Category("Test Category", user);
    categoryRepository.save(category);

    assertTrue(categoryRepository.existsById(category.getCategoryId()));

    categoryRepository.deleteById(category.getCategoryId());
    assertFalse(categoryRepository.existsById(category.getCategoryId()));
  }

  @Test
  void deleteUserTest() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    Category category = new Category("Test Category", user);
    categoryRepository.save(category);

    assertTrue(categoryRepository.existsById(category.getCategoryId()));

    userRepository.deleteById(user.getUserId());
    assertFalse(categoryRepository.existsById(category.getCategoryId()));
  }

  @Test
  void findCategoryByIdTest() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    Category category = new Category("Test Category", user);
    categoryRepository.save(category);

    Optional<Category> retrievedCategory = categoryRepository.findById(category.getCategoryId());
    assertTrue(retrievedCategory.isPresent());
    assertEquals("Test Category", retrievedCategory.get().getName());
  }

  @Test
  void findAllUserCategoriesTest() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    Category category1 = new Category("Test Category 1", user);
    categoryRepository.save(category1);
    Category category2 = new Category("Test Category 2", user);
    categoryRepository.save(category2);
    Category category3 = new Category("Test Category 3", user);
    categoryRepository.save(category3);

    userRepository.save(user);

    Optional<User> retrievedUser = userRepository.findById(user.getUserId());
    List<Category> categories = retrievedUser.get().getCategories();
    Assertions.assertEquals(3, categories.size());
    assertTrue(categories.get(0).getName().equals("Test Category 1"));
    assertTrue(categories.get(1).getName().equals("Test Category 2"));
    assertTrue(categories.get(2).getName().equals("Test Category 3"));
  }
}
