package com.example.api;

import com.example.model.User;
import com.example.model.UserData;
import com.example.model.UserId;
import com.example.model.UserName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User API", description = "Управление пользователями")
public interface UserApi {
  @Operation(summary = "Создать пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь создан")
  @PostMapping("/signup")
  ResponseEntity<UserId> createUser(@RequestBody UserData userData);


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


  @Operation(summary = "Обновить имя пользователя")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Имя обновлено"),
    @ApiResponse(responseCode = "404", description = "Пользователь с данным ID не существует")
  })
  @PatchMapping("/update/name/{userId}")
  ResponseEntity<User> updateUserName(
    @RequestBody UserName userName,
    @Parameter(name = "ID пользователя")
    @PathVariable Long userId
  );
}
