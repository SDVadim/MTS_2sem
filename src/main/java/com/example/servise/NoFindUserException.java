package com.example.servise;

public class NoFindUserException extends RuntimeException {
  public NoFindUserException(String message, Throwable cause) {
    super(message, cause);
  }
}