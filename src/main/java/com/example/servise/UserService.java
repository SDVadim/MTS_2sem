package com.example.servise;

import com.example.model.User;
import com.example.model.UserData;
import com.example.model.UserId;
import com.example.repoitory.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service

public class UserService {
  private UserRepository userRepository;

  public UserId createUser(UserData userData) {
    return userRepository.create(userData);
  }

  public void deleteUser(Long UserId) {
    userRepository.delete(UserId);
  }
}
