package com.pluralsight.conferencedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ConferenceDemoApplication {

  public static void main(String[] args) {
    log.trace("Starting Conference App ...");
    SpringApplication.run(ConferenceDemoApplication.class, args);
  }

}
