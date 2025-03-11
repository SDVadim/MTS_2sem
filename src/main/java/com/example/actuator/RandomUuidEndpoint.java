package com.example.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "uuid")
public class RandomUuidEndpoint {

  @ReadOperation
  public UUID getRandomUuid() {
    return UUID.randomUUID();
  }
}