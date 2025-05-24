
package com.example.servise;

import com.example.model.OutboxRecord;
import com.example.repository.OutboxRepository;
import com.example.servise.metrics.*;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.DistributionSummary;

import java.time.*;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxScheduler {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final OutboxRepository outboxRepository;
  private final OutboxMetrics outboxMetrics;

  @Value("${topic-to-send-message}")
  private String topic;


  @Transactional
  @Scheduled(fixedDelay = 10000)
  public void processOutbox() throws Exception {
    Instant startTime = Instant.now();
    List<OutboxRecord> result = outboxRepository.findAll();
    for (OutboxRecord outboxRecord : result) {
      CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, outboxRecord.getValue());
      log.info("Sending message to topic: " + outboxRecord.getValue());
      try {
        sendResult.get();
      } catch (Exception e) {
        throw new Exception("ошибка отправки сообщения в kafka", e);
      }
    }
    outboxRepository.deleteAll(result);
    Instant endTime = Instant.now();
    Duration duration = Duration.between(startTime, endTime);
    outboxMetrics.recordSendTime(duration.toMillis());
  }
}
