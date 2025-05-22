package com.example.model;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.*;
import java.util.*;

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
