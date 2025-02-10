package com.example.controller;

import com.example.model.UserData;
import com.example.model.UserId;
import com.example.servise.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
  private UserService userService;


  @PostMapping("/signup")
  public UserId createUser(@RequestBody UserData userData) {
    return userService.createUser(userData);
  }

  @DeleteMapping("/delete/{userId}")
  public void deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
  }

}
