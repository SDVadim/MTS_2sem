package com.example.servise;

import com.example.model.Action;
import com.example.model.DtoMessage;
import com.example.model.User;
import com.example.model.request.UserData;
import com.example.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
@AllArgsConstructor
public class UsersService {
  private final UserRepository userRepository;
  private final KafkaProducerService kafkaProducerService;

  @Transactional
  @Retryable(retryFor = NoFindUserException.class, maxAttempts = 5, backoff = @Backoff(delay = 10_000))
  public User createUser(UserData userData) throws JsonProcessingException {
    log.info("Creating User with name {}", userData.getName());

    User user = new User(userData.getName(), userData.getPassword());
    log.info("User created: {}", user);

    userRepository.save(user);
    log.info("User saved: {}", user);

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(user.getUserId())
        .eventTime(Instant.now())
        .eventType(Action.INSERT.name())
        .eventDetails("Пользователь создан")
        .build());

    return user;
  }

  @Transactional
  public void deleteUser(Long userId) throws JsonProcessingException {
    if (!userRepository.existsById(userId)) {
      log.error("User with id {} not found", userId);
      throw new NoFindUserException("User not found");
    }

    userRepository.deleteById(userId);
    log.info("User with id {} deleted", userId);

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.DELETE.name())
        .eventDetails("Пользователь удален")
        .build());
  }

  @Transactional
  public User getUserByUserId(Long userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NoFindUserException("User not found"));
    log.info("User found: {}", user);
    return user;
  }

  @Transactional
  public User updateUser(UserData userData, Long userId) throws JsonProcessingException {
    userRepository.deleteById(userId);
    log.info("User with id {} deleted for update", userId);

    User user = new User(userData.getName(), userData.getPassword());
    log.info("Updated user created: {}", user);

    kafkaProducerService.sendAuditMessage(DtoMessage.builder()
        .userId(userId)
        .eventTime(Instant.now())
        .eventType(Action.UPDATE.name())
        .eventDetails("Обновили информацию о пользователе")
        .build());

    return userRepository.save(user);
  }
}
