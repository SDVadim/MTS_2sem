package com.example.servise.metrics;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxMetrics {

  private final MeterRegistry registry;

  private DistributionSummary sendSummary;

  @PostConstruct
  public void init() {
    this.sendSummary = DistributionSummary
        .builder("outbox_kafka_time")
        .description("Время, затраченное на отправку записей из outbox в Kafka")
        .serviceLevelObjectives(1, 5, 10, 20, 50, 100, 200, 300, 400, 500)
        .register(registry);
  }

  public void recordSendTime(double durationMs) {
    sendSummary.record(durationMs);
  }
}
