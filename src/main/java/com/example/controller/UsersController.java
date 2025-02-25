package com.example.controller;

import com.example.api.UserApi;
import com.example.model.*;
import com.example.servise.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController implements UserApi {
  private UsersService usersService;

  @Override
  public ResponseEntity<UserId> createUser(UserData userData) {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.createUser(userData));
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long userId) {
    usersService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<User> updateUser(UserData userData, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(userData, userId));
  }

  @Override
  public ResponseEntity<User> updateUserName(UserName userName, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUserName(userId, userName));
  }

  @Override
  public ResponseEntity<User> updateUserPassword(UserPassword userPassword, Long userId) {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUserPassword(userId, userPassword));
  }
}
