package com.example.servise;

import com.example.model.*;
import com.example.repoitory.DbUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersService {
  private DbUserRepository userRepository;

  public UserId createUser(UserData userData) {
    return userRepository.createUser(userData);
  }

  public void deleteUser(Long userId) {
    userRepository.deleteUser(new UserId(userId));
  }

  public User getUser(Long userId) {
    return userRepository.getUser(new UserId(userId));
  }

  public User updateUser(UserData userData, Long userId) {
    UserId id = new UserId(userId);
    userRepository.deleteUser(id);
    return userRepository.updateUser(id, userData);
  }

  public User updateUserName(Long userId, UserName name) {
    UserId id = new UserId(userId);
    User user = userRepository.getUser(new UserId(userId));
    userRepository.deleteUser(id);
    User newUser = User.builder().userId(id).password(user.getPassword()).name(name.getName()).build();
    userRepository.updateSmth(newUser);
    return newUser;
  }

  public User updateUserPassword(Long userId, UserPassword password) {
    UserId id = new UserId(userId);
    User user = userRepository.getUser(id);
    userRepository.deleteUser(id);
    User newUser = User.builder().name(user.getName()).userId(id).password(password.getPassword()).build();
    userRepository.updateSmth(newUser);
    return newUser;
  }
}
