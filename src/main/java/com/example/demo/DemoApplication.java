package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class DemoApplication {

    public static void main(String[] args) {
        System.setProperty("javax.net.ssl.trustStore", "/Users/sony/Downloads/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        SpringApplication.run(DemoApplication.class, args);
    }

}
