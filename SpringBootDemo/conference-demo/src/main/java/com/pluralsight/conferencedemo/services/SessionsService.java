package com.pluralsight.conferencedemo.services;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.models.entity.Session;
import com.pluralsight.conferencedemo.models.mapper.SessionMapper;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionsService {

  private final SessionRepository sessionRepository;

  @Autowired
  public SessionsService(SessionRepository sessionRepository) {
    this.sessionRepository = sessionRepository;
  }

  public List<SessionDto> getAllSession() {
    try {
      List<Session> sessionList = sessionRepository.findAll();
      return sessionList.stream().map(SessionMapper.INSTANCE::toDto).toList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  public SessionDto getSessionById(Long id) {
    try {
      Optional<Session> sessionOptional = sessionRepository.findById(id);
      if (sessionOptional.isPresent()) {
        Session session = sessionOptional.get();
        return SessionMapper.INSTANCE.toDto(session);
      } else {
        // handle if id not found
        // throw custom exception or null
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public SessionDto createSession(SessionDto sessionDto) {
    log.trace("Creating sessionName: " + sessionDto.getSessionName());
    log.trace("Creating sessionDescription : " + sessionDto.getSessionDescription());
    log.trace("Creating sessionLength : " + sessionDto.getSessionLength());
    try {
      Session sessionEntity = SessionMapper.INSTANCE.toEntity(sessionDto);
      Session saveSession = sessionRepository.saveAndFlush(sessionEntity);
      return SessionMapper.INSTANCE.toDto(saveSession);
    } catch (Exception e) {
      log.error("Error when saving session: " + e.getMessage());
      return null;
    }
  }

  public void deleteSessionById(Long id) {
    log.trace("Entering deleteSessionById() with id: " + id);

    try {
      Optional<Session> sessionOptional = sessionRepository.findById(id);
      if (sessionOptional.isPresent()) {
        log.debug("enter deleteSessionById sessionOptional.isPresent() IF");
        sessionRepository.deleteById(id);
      } else {
        log.debug("enter deleteSessionById sessionOptional.isPresent() ELSE");
      }
    } catch (Exception e) {
      log.error("error deleting session with id " + id + ": " + e.getMessage());
    }
  }

  public SessionDto updateSessionById(Long id, SessionDto sessionDto) {
    log.trace("Entering updateSessionById");
    if (null == sessionDto) {
      log.debug("sessionDto is null");
      return null;
    }
    try {
      Optional<Session> sessionOptional = sessionRepository.findById(id);
      if (sessionOptional.isPresent()) {
        Session existingSession = sessionOptional.get();
        Session sessionToUpdate = SessionMapper.INSTANCE.toEntity(sessionDto);
        BeanUtils.copyProperties(sessionToUpdate, existingSession, "session_id");
        Session updatedSession = sessionRepository.saveAndFlush(existingSession);
        return SessionMapper.INSTANCE.toDto(updatedSession);
      } else {
        log.debug("Update Session fail with id " + id);
        log.debug("sessionId: " + sessionDto.getSessionId());
        log.debug("sessionName: " + sessionDto.getSessionName());
        log.debug("sessionDescription: " + sessionDto.getSessionDescription());
        log.debug("sessionLength: " + sessionDto.getSessionLength());
        return null;
      }
    } catch (Exception e) {
      log.error("error update session with id " + id + ": " + e.getMessage());
      return null;
    }
  }
}
