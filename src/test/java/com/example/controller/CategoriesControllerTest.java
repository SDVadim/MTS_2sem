package com.example.controller;

import com.example.MtsHmApplication;
import com.example.model.*;
import com.example.model.request.CategoryData;
import com.example.security.SecurityConfig;
import com.example.servise.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CategoriesController.class)
@ContextConfiguration(classes = {MtsHmApplication.class, SecurityConfig.class })
class CategoriesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService categoryService;

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testGetCategory() throws Exception {
    Category mockCategory = new Category("java", new User("Vadim", "1234"));

    when(categoryService.findCategoryById(1L)).thenReturn(mockCategory);

    mockMvc.perform(get("/api/categories/{categoryId}", 1))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").value("java"));

  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testCreateCategory() throws Exception {

    when(categoryService.createCategory(any(CategoryData.class), any(Long.class))).thenReturn(new Category("java", new User("Vadim", "1234")));

    mockMvc.perform(post("/api/categories/create/{userId}", 1)
        .contentType("application/json")
        .content("{\"name\":\"Vadim\"}"))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").value("java"));
  }
}