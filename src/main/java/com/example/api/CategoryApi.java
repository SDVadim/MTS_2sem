package com.example.api;

import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category API", description = "Управление категориями")
public interface CategoryApi {
  @Operation(summary = "Обновить категорию")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Данные обновлены"),
    @ApiResponse(responseCode = "404", description = "Категория с данным ID не существует")
  })
  @PutMapping("/update/{categoryId}")
  ResponseEntity<Category> updateCategory(
    @RequestBody CategoryData categoryData,
    @Parameter(description = "ID категории")
    @PathVariable Long categoryId
  );


  @Operation(summary = "Создать категорию для пользователя")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Данные обновлены"),
    @ApiResponse(responseCode = "404", description = "Категория с данным ID не существует")
  })
  @PostMapping("/create/{userId}")
  ResponseEntity<CategoryId> createCategory(
    @RequestBody CategoryData categoryData,
    @Parameter(description = "ID пользователя")
    @PathVariable Long userId
  );


  @Operation(summary = "Получить все доступные категории для данного пользователя")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Все категории получены"),
    @ApiResponse(responseCode = "404", description = "Пользователь с данным Id не найден")
  })
  @PostMapping("/user/{userId}")
  ResponseEntity<List<Category>> getAllCategories(
    @Parameter(description = "ID пользователя")
    @PathVariable Long userId
  );

  @Operation(summary = "Получить категорию по ID")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Категория найдена"),
    @ApiResponse(responseCode = "404", description = "Категория с данным ID не существует")
  })
  @GetMapping("/{categoryId}")
  ResponseEntity<Category> getCategory(
    @Parameter(description = "ID категории")
    @PathVariable Long categoryId
  );


  @Operation(summary = "Удалить категорию по ID")
  @ApiResponse(responseCode = "200", description = "Категория удалена")
  @DeleteMapping("/delete/{categoryId}")
  ResponseEntity<Void> deleteCategory(
    @Parameter(description = "Категория ID")
    @PathVariable Long categoryId
  );


  @Operation(summary = "Удалить пользователя")
  @ApiResponse(responseCode = "200", description = "Пользователь удален")
  @DeleteMapping("/delete/user/{userId}")
  ResponseEntity<Void> deleteUser(
    @Parameter(description = "ID пользователя") @PathVariable Long userId
  );
}
