package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoriesControllerE2ETest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testCreateAndGetCategory() throws Exception {
    String categoryJson = "{\"name\":\"Vadim\"}";

    mockMvc.perform(post("/api/categories/create/{userId}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .content(categoryJson))
      .andExpect(status().isOk());

    mockMvc.perform(get("/api/categories/{categoryId}", 1))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.name").value("Vadim"))
      .andExpect(jsonPath("$.userId.userId").value(1));
  }
}