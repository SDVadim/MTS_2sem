package com.example.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Table(name = "outbox")
@Entity
@Getter
@NoArgsConstructor
public class OutboxRecord {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @NotNull
  private String value;

  public OutboxRecord(String value) {
    this.value = value;
  }
}