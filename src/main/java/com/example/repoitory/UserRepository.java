package com.example.repoitory;

import com.example.model.UserData;
import com.example.model.UserId;

public interface UserRepository {
  UserId createUser(UserData userData);

  void deleteUser(UserId userId);
}
