package com.example.servise;

import com.example.model.DtoMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.apache.kafka.common.serialization.StringDeserializer;


import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

@Testcontainers
@SpringBootTest
public class KafkaProducerServiceTest {

  @Container
  public static final KafkaContainer KAFKA = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

  @DynamicPropertySource
  static void setProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    registry.add("topic-to-send-message", () -> "test-topic");
  }

  @TestConfiguration
  static class TestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      return mapper;
    }
  }

  @Autowired
  private KafkaProducerService kafkaProducerService;

  @Autowired
  private ObjectMapper objectMapper;


  @Test
  void shouldSendMessageToKafkaSuccessfully() throws Exception {

    DtoMessage testDtoMessage = DtoMessage.builder()
        .userId(123L)
        .eventTime(Instant.now())
        .eventType("LOGIN")
        .eventDetails("User logged in")
        .build();

    String topic = kafkaProducerService.getTopic();

    assertDoesNotThrow(() -> kafkaProducerService.sendAuditMessage(testDtoMessage));

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "test-group");
    consumer.subscribe(List.of(topic));

    ConsumerRecords<String, String> records = consumer.poll();
    assertEquals(1, records.count());

    records.forEach(record -> {
      try {
        DtoMessage received = objectMapper.readValue(record.value(), DtoMessage.class);
        assertEquals(testDtoMessage.getUserId(), received.getUserId());
        assertEquals(testDtoMessage.getEventType(), received.getEventType());
        assertEquals(testDtoMessage.getEventDetails(), received.getEventDetails());
        assertNotNull(received.getEventTime());
      } catch (Exception e) {
        fail("Deserialization failed: " + e.getMessage());
      }
    });
  }


  public class KafkaTestConsumer {
    private final KafkaConsumer<String, String> consumer;

    public KafkaTestConsumer(String bootstrapServers, String groupId) {
      Properties props = new Properties();

      props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
      props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
      props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
      props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

      this.consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(List<String> topics) {
      consumer.subscribe(topics);
    }

    public ConsumerRecords<String, String> poll() {
      return consumer.poll(Duration.ofSeconds(5));
    }

  }
}
