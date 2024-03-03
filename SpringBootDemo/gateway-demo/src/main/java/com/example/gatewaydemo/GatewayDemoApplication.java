package com.example.gatewaydemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class GatewayDemoApplication {

  public static void main(String[] args) {
    log.trace("Starting Gateway ...");
    SpringApplication.run(GatewayDemoApplication.class, args);
  }

}
