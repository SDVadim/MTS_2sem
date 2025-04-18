package com.example.api;

import com.example.model.*;
import com.example.model.request.UserData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public interface UserApi {
  @Operation(summary = "Создать пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь создан")
  @PostMapping("/signup")
  ResponseEntity<User> createUser(@RequestBody UserData userData);


  @Operation(summary = "Удалить пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь удален")
  @DeleteMapping("/delete/{userId}")
  ResponseEntity<Void> deleteUser(
    @Parameter(name = "ID пользователя")
    @PathVariable Long userId
  );


  @Operation(summary = "Обновить пользователя")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Пользователь создан"),
    @ApiResponse(responseCode = "404", description = "Пользователь с данным ID не существует")
  })
  @PutMapping("/update/{userId}")
  ResponseEntity<User> updateUser(
    @RequestBody UserData userData,
    @Parameter(name = "ID пользователя")
    @PathVariable Long userId
  );
}
