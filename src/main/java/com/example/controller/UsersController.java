package com.example.controller;

import com.example.api.UserApi;
import com.example.model.*;
import com.example.model.request.UserData;
import com.example.servise.UsersService;
import com.fasterxml.jackson.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController implements UserApi {
  private UsersService usersService;

  public UsersController(UsersService usersService) {
    this.usersService = usersService;
  }

  @Override
  public ResponseEntity<User> createUser(UserData userData) throws JsonProcessingException {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.createUser(userData));
  }

  @Override
  public ResponseEntity<Void> deleteUser(Long userId) throws JsonProcessingException {
    usersService.deleteUser(userId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<User> updateUser(UserData userData, Long userId) throws JsonProcessingException {
    return ResponseEntity.status(HttpStatus.OK).body(usersService.updateUser(userData, userId));
  }
}
