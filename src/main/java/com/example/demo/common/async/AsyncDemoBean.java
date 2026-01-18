package com.example.demo.common.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class AsyncDemoBean {
  @SneakyThrows
  @Async
  public void asyncLogger() {
    log.info("Entering asyncLogger method in AsyncDemoBean");
    log.info("Going to sleep for 2 seconds in AsyncDemoBean::asyncLogger");
    Thread.sleep(2000);
    log.info(
        "AsyncDemoBean::asyncLogger This is an async log message from AsyncDemoBean after sleeping 2 seconds");
    log.info("AsyncDemoBean::asyncLogger Exiting asyncLogger method in AsyncDemoBean");
  }

  @SneakyThrows
  @Async
  public CompletableFuture<String> asyncLoggerWithReturn() {
    log.info("Entering asyncLoggerWithReturn method in AsyncDemoBean");
    log.info("Going to sleep for 4 seconds in AsyncDemoBean::asyncLoggerWithReturn");
    Thread.sleep(4000);
    log.info(
        "AsyncDemoBean::asyncLoggerWithReturn This is an async log message from AsyncDemoBean after sleeping 4 seconds");
    String returnString = System.currentTimeMillis() + " - Async Task Completed";
    log.info("AsyncDemoBean::asyncLoggerWithReturn returnString = " + returnString);
    log.info(
        "AsyncDemoBean::asyncLoggerWithReturn Exiting asyncLoggerWithReturn method in AsyncDemoBean");
    return CompletableFuture.completedFuture(returnString);
  }
}
