package com.example.repository;

import com.example.model.*;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
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
  void saveUserAndFindThemTest() {
    User user = new User("user_1234", "1234");

    User savedUser = userRepository.save(user);

    Optional<User> retrievedUser = userRepository.findById(savedUser.getUserId());

    assertTrue(retrievedUser.isPresent());
    assertEquals("user_1234", retrievedUser.get().getName());
    assertEquals("1234", retrievedUser.get().getPassword());
  }

  @Test
  void deleteUser() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);
    assertTrue(userRepository.existsById(user.getUserId()));

    userRepository.deleteById(user.getUserId());
    assertFalse(userRepository.existsById(user.getUserId()));
  }

  @Test
  void updateUser() {
    User user = new User("user_1234", "1234");
    userRepository.save(user);

    user.setName("updated_name");
    user.setPassword("updated_password");
    userRepository.save(user);

    Optional<User> updatedUser = userRepository.findById(user.getUserId());

    assertTrue(updatedUser.isPresent());
    assertEquals("updated_name", updatedUser.get().getName());
    assertEquals("updated_password", updatedUser.get().getPassword());
  }
}