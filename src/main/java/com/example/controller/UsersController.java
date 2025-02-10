package com.example.controller;

import com.example.model.UserData;
import com.example.model.UserId;
import com.example.servise.UsersService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UsersController {
  private static final Logger LOG = LoggerFactory.getLogger(UsersController.class);
  private final UsersService usersService;


  @PostMapping("/signup")
  public UserId createUser(@RequestBody UserData userData) {
    return usersService.createUser(userData);
  }

  @DeleteMapping("/delete/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    usersService.deleteUser(userId);
  }

}
