package com.example.servise;

import com.example.model.*;
import com.example.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Getter
public class KafkaProducerService {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;
  private final OutboxRepository outboxRepository;

  @Value("${topic-to-send-message}")
  private String topic;

  public void sendAuditMessage(DtoMessage dtoMessage) throws JsonProcessingException {
    String message = objectMapper.writeValueAsString(dtoMessage);
    outboxRepository.save(new OutboxRecord(message));
  }
}