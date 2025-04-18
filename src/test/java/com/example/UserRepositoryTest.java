package com.example;

import com.example.model.*;
import com.example.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.*;
import org.springframework.stereotype.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import jakarta.validation.ConstraintViolationException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.*;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
@ActiveProfiles("test")
class UserRepositoryTest {
  @Autowired
  UserRepository userRepository;

  @Container
  @ServiceConnection
  static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13");



  @Test
  @DisplayName("Тест сохранения пользователя")
  void saveUserTest() {
    User user = new User("user_1234", "1234");

    User savedUser = userRepository.save(user);

    Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());

    assertTrue(retrievedUser.isPresent());
    assertEquals("user_1234", retrievedUser.get().getName());
    assertEquals("1234", retrievedUser.get().getPassword());
  }
}