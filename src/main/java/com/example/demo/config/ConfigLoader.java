package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:demoapp.properties")
@PropertySource("classpath:KafkaProducerSsl.properties")
@PropertySource("classpath:KafkaConsumerSsl.properties")
public class ConfigLoader {
}
