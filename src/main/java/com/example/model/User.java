package com.example.model;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class User {
  private UserId userId;
  private String name;
  private String password;
}
