package com.example.demo.service;

import com.example.demo.common.async.AsyncDemoBean;
import com.example.demo.model.config.KafkaProducerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class CheckService {

  // checking the use of @ConfigurationProperties with KafkaProducerConfig
  private final KafkaProducerConfig kafkaProducerConfig;
  private final AsyncDemoBean asyncDemoBean;

  // Checking the Spring Expression language use
  @Value("#{kafkaProducerConfig.sslProperties.get('salesKafka').keystoreLocation}")
  private String salesKeystoreLocation;

  public CheckService(KafkaProducerConfig kafkaProducerConfig, AsyncDemoBean asyncDemoBean) {
    this.kafkaProducerConfig = kafkaProducerConfig;
    this.asyncDemoBean = asyncDemoBean;
    log.info("CheckService initialized with KafkaProducerConfig");
  }

  public String checkPing() {
    System.out.println("ping in the service");
    String response = "ping time " + System.currentTimeMillis();
    response = response + "\n" + extractConfigPropValues();
    log.info("response = " + response);
    callAsyncmethods();
    return response;
  }

  private String extractConfigPropValues() {
    String kafkaProducerConfigValue = kafkaProducerConfig.toString();
    log.info("kafkaProducerConfigValue = " + kafkaProducerConfigValue);
    log.info("salesKeystoreLocation = " + salesKeystoreLocation);
    return "kafkaProducerConfigValue = "
        + kafkaProducerConfigValue
        + "\n"
        + "salesKeystoreLocation = "
        + salesKeystoreLocation;
  }

  private void callAsyncmethods() {
      log.info("Calling asyncLogger from CheckService");
      asyncDemoBean.asyncLogger();
      log.info("Called asyncLogger from CheckService");
      log.info("Calling asyncLoggerWithReturn from CheckService");
      CompletableFuture<String> stringCompletableFuture = asyncDemoBean.asyncLoggerWithReturn();
      log.info("Called asyncLoggerWithReturn from CheckService");
      log.info("calling isDone on the CompletableFuture returned from asyncLoggerWithReturn");
      boolean done = stringCompletableFuture.isDone();
      log.info("isDone = " + done);
      log.info("calling stringCompletableFuture.thenAccept");
      // Attach a callback to handle the result when it becomes available
      stringCompletableFuture.thenAccept(result -> log.info("Result from asyncLoggerWithReturn: " + result));
      log.info("this line is after the stringCompletableFuture.thenAccept");
  }
}
