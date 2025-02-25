package com.example.repoitory;

import com.example.model.User;
import com.example.model.UserData;
import com.example.model.UserId;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@AllArgsConstructor
public class DbUserRepository implements UserRepository{
  private final Logger LOG = LoggerFactory.getLogger(DbUserRepository.class);
  private final List<User> userList = new ArrayList<>();
  private final AtomicLong id = new AtomicLong(0);

  private UserId generateId() {
    LOG.info("Создали ID для пользователя");
    return new UserId(id.incrementAndGet());
  }

  public UserId createUser(UserData userData) {
    UserId userId = generateId();
    User user = User.builder().userId(userId).name(userData.getName()).password(userData.getPassword()).build();
    userList.add(user);
    LOG.info("Создали пользователя: {} с ID: {}", userData.getName(), userId);
    return userId;
  }

  public void deleteUser(UserId userId) {
    UserId id = generateId();
    for (User user : userList) {
      if (user.getUserId().equals(id)) {
        userList.remove(user);
        LOG.info("Пользователь с id: {} удален", userId);
      }
    }
  }

  public User getUser(UserId userId) {
    for (User user : userList) {
      if (user.getUserId().equals(userId)) {
        LOG.info("Пользователь с ID: {} найден", userId);
        return user;
      }
    }
    LOG.info("Пользователь с данным ID не найден");
    return null;
  }

  public User updateUser(UserId userId, UserData userData) {
    User newUser = User.builder().userId(userId).name(userData.getName()).password(userData.getPassword()).build();
    userList.add(newUser);
    LOG.info("Обновили данные пользователя: {} с ID: {}", userData.getName(), userId);
    return newUser;
  }

  public void updateSmth(User user) {
    userList.add(user);
    LOG.info("Пользователь обновлен");
  }
}
