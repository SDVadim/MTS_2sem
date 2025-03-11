package com.example.api;

import com.example.model.Article;
import com.example.model.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Article API", description = "Управление статьями")
public interface ArticleApi {
  @Operation(summary = "Получить все статьи по id пользователя")
  @ApiResponse(responseCode = "200", description = "Статьи найдены")
  @GetMapping("/{id}")
  ResponseEntity<Map<Article, Category>> getArticles(
      @Parameter(description = "ID ")
      @PathVariable Long uerId
  );
}
