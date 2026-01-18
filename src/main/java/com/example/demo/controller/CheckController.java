package com.example.demo.controller;

import com.example.demo.service.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class CheckController {

  private final CheckService checkService;

  public CheckController(CheckService checkService) {
    this.checkService = checkService;
    log.info("CheckController initialized with CheckService");
  }

  @GetMapping(
      path = "/ping",
      produces = {"application/json"})
  public String ping() {
    return checkService.checkPing();
  }
}
