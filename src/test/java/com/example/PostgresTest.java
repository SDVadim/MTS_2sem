package com.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Testcontainers
public class PostgresTest {

  @Container
  private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:13")
      .withDatabaseName("testdb")
      .withUsername("user")
      .withPassword("password")
      .withInitScript("db-init.sql");

  @DynamicPropertySource
  public static void registerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @BeforeAll
  public static void logDbInfo() {
    log.info("PostgreSQL контейнер хост: {}", postgres.getHost() + ":" + postgres.getFirstMappedPort());
    log.info("PostgreSQL URL соединения: {}", postgres.getJdbcUrl());
  }

  @Test
  public void loggingPort() {
    assertTrue(postgres.isRunning());
  }
}