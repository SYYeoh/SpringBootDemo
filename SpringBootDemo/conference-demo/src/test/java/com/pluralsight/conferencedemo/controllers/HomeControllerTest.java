package com.pluralsight.conferencedemo.controllers;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(locations="classpath:application.yaml")
class HomeControllerTest {
  @InjectMocks
  HomeController homeController;

  @Test
  void testHello() {
    String actual = homeController.hello();
    assertEquals("Hello from Conference App", actual);
  }
}
