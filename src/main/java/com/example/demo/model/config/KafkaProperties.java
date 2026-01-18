package com.example.demo.model.config;

import lombok.Data;

@Data
public class KafkaProperties {
    private String truststoreLocation;
    private String truststorePassword;
    private String keystoreLocation;
    private String keystorePassword;
}
