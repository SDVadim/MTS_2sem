package com.example.repoitory;

import com.example.model.Category;
import com.example.model.CategoryId;
import com.example.model.UserId;
import java.util.List;

public interface CategoryRepository {
  CategoryId createCategory(String name, UserId userId);

  List<Category> findAllCategories(UserId userId);

  Category getCategory(CategoryId categoryId);

  void deleteCategory(CategoryId categoryId);

  void deleteUser(UserId userId);
}
