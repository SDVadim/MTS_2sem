package com.example.servise;

import com.example.model.*;
import com.example.model.request.UserData;
import com.example.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService {
  private final UserRepository userRepository;

  @Transactional
  @Retryable(retryFor = NoFindUserException.class, maxAttempts = 5, backoff = @Backoff(delay = 10_000))
  public User createUser(UserData userData) {
    log.info("Creating User with name {}", userData.getName());

    User user = new User(userData.getName(), userData.getPassword());
    log.info("User created: {}", user);

    userRepository.save(user);
    log.info("User saved: {}", user);

    return user;
  }

  @Transactional
  public void deleteUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      log.error("User with id {} not found", userId);
      throw new NoFindUserException("User not found");
    }

    userRepository.deleteById(userId);
    log.info("User with id {} deleted", userId);
  }

  @Transactional
  public User getUserByUserId(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NoFindUserException("User not found"));
    log.info("User found: {}", user);
    return user;
  }

  @Transactional
  public User updateUser(UserData userData, Long userId) {
    userRepository.deleteById(userId);
    log.info("User with id {} deleted for update", userId);

    User user = new User(userData.getName(), userData.getPassword());
    log.info("Updated user created: {}", user);

    return userRepository.save(user);
  }
}
