package com.example.bookmanagementsystem.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HomeControllerTest {
  @InjectMocks
  HomeController homeController;

  @Test
  void testHello() {
    String actual = homeController.hello();
    assertEquals("Hello from Book Management", actual);
  }

}
