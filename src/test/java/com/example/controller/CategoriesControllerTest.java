package com.example.controller;

import com.example.MtsHmApplication;
import com.example.model.Category;
import com.example.model.CategoryData;
import com.example.model.CategoryId;
import com.example.model.UserId;
import com.example.security.SecurityConfig;
import com.example.servise.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
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

  @MockitoBean
  private CategoryService categoryService;

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testGetCategory() throws Exception {
    Category mockCategory = Category.builder().categoryId(new CategoryId(1)).name("HM_2").userId(new UserId(1)).build();

    when(categoryService.getCategory(1L)).thenReturn(mockCategory);

    mockMvc.perform(get("/api/categories/{categoryId}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("HM_2"))
        .andExpect(jsonPath("$.userId.userId").value(1));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testCreateCategory() throws Exception {

    when(categoryService.createCategory(any(CategoryData.class), any(Long.class))).thenReturn(new CategoryId(1));

    mockMvc.perform(post("/api/categories/create/{userId}", 1)
            .contentType("application/json")
            .content("{\"name\":\"Vadim\"}"))
        .andExpect(status().isOk());
  }
}