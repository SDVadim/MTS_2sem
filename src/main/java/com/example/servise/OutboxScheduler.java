package com.example.servise;

import com.example.model.OutboxRecord;
import com.example.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxScheduler {
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final OutboxRepository outboxRepository;

  @Value("${topic-to-send-message}")
  private String topic;

  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() {
    List<OutboxRecord> result = outboxRepository.findAll();
    for (OutboxRecord outboxRecord : result) {
      CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, outboxRecord.getValue());
      log.info("Sending message to topic: " + outboxRecord.getValue());
      try {
        sendResult.get();
      } catch (Exception e) {
        throw new RuntimeException("Ошибка при отправке сообщения в Kafka", e);
      }
    }
    outboxRepository.deleteAll(result);
  }
}
