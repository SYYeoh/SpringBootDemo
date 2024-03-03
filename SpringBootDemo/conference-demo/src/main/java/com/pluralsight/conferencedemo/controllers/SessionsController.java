package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sessions")
@Slf4j
public class SessionsController {

  @Autowired
  private SessionRepository sessionRepository;

  @GetMapping
  public List<Session> list() {
    log.trace("Entering list()");
    return sessionRepository.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Session get(@PathVariable Long id) {
    log.trace("get session id: " + id);
    return sessionRepository.getOne(id);
  }

  @PostMapping
  public Session create(@RequestBody final Session session) {
    log.trace("Creating sessionName: " + session.getSessionName());
    log.trace("Creating sessionDescription : " + session.getSessionDescription());
    log.trace("Creating sessionLength : " + session.getSessionLength());
    return sessionRepository.saveAndFlush(session);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id) {
    //Also need to check for children records before deleting.
    log.trace("Entering delete() with id: " + id);
    sessionRepository.deleteById(id);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public Session update(@PathVariable Long id, @RequestBody Session session) {
    //because this is a PUT, we expect all attributes to be passed in.
    // A PATCH would only need what has changed.
    //TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
    log.trace("entering update()");
    log.trace("sessionId: " + session.getSessionId());
    log.trace("sessionName: " + session.getSessionName());
    log.trace("sessionDescription: " + session.getSessionDescription());
    log.trace("sessionLength: " + session.getSessionLength());
    Session existingSession = sessionRepository.getOne(id);
    BeanUtils.copyProperties(session, existingSession, "session_id");
    return sessionRepository.saveAndFlush(existingSession);
  }

}
