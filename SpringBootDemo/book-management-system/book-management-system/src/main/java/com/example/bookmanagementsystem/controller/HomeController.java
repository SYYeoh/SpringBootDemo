package com.example.bookmanagementsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-book")
@Slf4j
public class HomeController {
  @GetMapping
  public String hello() {
    log.trace("Entering hello()");
    return "Hello from Book Management";
  }
}
