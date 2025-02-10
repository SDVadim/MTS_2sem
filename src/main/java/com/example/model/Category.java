package com.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category{
  CategoryId categoryId;
  String name;
  UserId userId;
}
