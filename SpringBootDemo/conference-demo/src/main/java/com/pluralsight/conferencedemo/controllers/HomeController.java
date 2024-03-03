package com.pluralsight.conferencedemo.controllers;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-conference")
@Slf4j
public class HomeController {

  @Value("${app.version}")
  private String appVersion;


  @GetMapping
  @RequestMapping("/")
  public Map getStatus() {
    log.trace("Entering getStatus()");
    Map map = new HashMap<String, String>();
    map.put("app-version", appVersion);
    return map;
  }

  @GetMapping
  public String hello() {
    log.trace("Entering hello()");
    return "Hello from Conference App";
  }

}
