package com.example.demo.model.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "myapp.kafka.producer")
@Component
public class KafkaProducerConfig {
    private String bootstrapServers;
    private String keySerializer;
    private String valueSerializer;
    private Map<String,KafkaProperties> sslProperties;
}
