package com.example.servise;

import com.example.model.UserData;
import com.example.model.UserId;
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
}
