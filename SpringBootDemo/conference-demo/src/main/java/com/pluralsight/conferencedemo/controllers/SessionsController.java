package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.services.SessionsService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-conference/api/v1/sessions")
@Slf4j
public class SessionsController {

  private final SessionsService sessionsService;

  public SessionsController(SessionsService sessionsService) {
    this.sessionsService = sessionsService;
  }

  @GetMapping
  public List<SessionDto> list() {
    log.trace("Entering list()");
    return sessionsService.getAllSession();
  }

  @GetMapping("{id}")
  public SessionDto get(@PathVariable Long id) {
    log.trace("get session id: " + id);
    return sessionsService.getSessionById(id);
  }

  @PostMapping
  public SessionDto create(@RequestBody final SessionDto sessionDto) {
    return sessionsService.createSession(sessionDto);
  }

  @DeleteMapping(value = "{id}")
  public void delete(@PathVariable Long id) {
    //Also need to check for children records before deleting.
    log.trace("Entering delete() with id: " + id);
    sessionsService.deleteSessionById(id);
  }

  @PutMapping(value = "{id}")
  public SessionDto update(@PathVariable Long id, @RequestBody SessionDto sessionDto) {
    //because this is a PUT, we expect all attributes to be passed in.
    // A PATCH would only need what has changed.
    log.trace("entering update()");
    log.trace("sessionId: " + sessionDto.getSessionId());
    log.trace("sessionName: " + sessionDto.getSessionName());
    log.trace("sessionDescription: " + sessionDto.getSessionDescription());
    log.trace("sessionLength: " + sessionDto.getSessionLength());

    return sessionsService.updateSessionById(id, sessionDto);
  }

}
