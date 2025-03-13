package com.example.servise;

public class NoFindUserException extends RuntimeException {
  public NoFindUserException(String message) {
    super(message);
  }
}