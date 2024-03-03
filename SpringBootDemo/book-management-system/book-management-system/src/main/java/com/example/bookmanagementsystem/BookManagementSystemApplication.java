package com.example.bookmanagementsystem;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BookManagementSystemApplication {

  public static void main(String[] args) {
    log.info("Starting book management ...");
    SpringApplication.run(BookManagementSystemApplication.class, args);
  }

}
