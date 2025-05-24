package com.example.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DtoMessage {
  @NotNull
  private Long userId;

  @NotNull
  private Instant eventTime;

  @NotNull
  private String eventType;


  private String eventDetails;
}
