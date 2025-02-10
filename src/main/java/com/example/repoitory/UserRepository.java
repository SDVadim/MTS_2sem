package com.example.repoitory;

import com.example.model.User;
import com.example.model.UserData;
import com.example.model.UserId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@AllArgsConstructor
public class UserRepository {

  private final List<User> userRepository = new ArrayList<>();
  private final AtomicLong id = new AtomicLong(0);

  private UserId generateId() {
    return new UserId(id.incrementAndGet());
  }

  public UserId create(UserData userData) {
    UserId id = generateId();
    User user = User.builder().name(userData.name()).password(userData.password()).build();
    userRepository.add(user);
    return id;
  }

  public void delete(Long userId) {
    UserId id = new UserId(userId);
    for (User user : userRepository) {
      if (user.getUserId() == id) {
        userRepository.remove(user);
      }
    }
  }
}
