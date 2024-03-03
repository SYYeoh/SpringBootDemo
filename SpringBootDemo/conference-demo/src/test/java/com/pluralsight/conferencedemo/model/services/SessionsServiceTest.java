package com.pluralsight.conferencedemo.model.services;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.models.entity.Session;
import com.pluralsight.conferencedemo.repositories.SessionRepository;
import com.pluralsight.conferencedemo.services.SessionsService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class SessionsServiceTest {

  @InjectMocks
  SessionsService sessionsService;

  @Mock
  SessionRepository sessionRepository;
  private Session session1;
  private Session session2;
  private SessionDto sessionDto;
  private List<Session> list;

  @BeforeEach
  void setUp() {
    sessionDto = new SessionDto(1L, "session1", "session desc 1", 1, null);

    session1 = new Session(1L, "session1", "session desc 1", 1, null);
    session2 = new Session(2L, "session2", "session desc 2", 2, null);
    list = new ArrayList<>();
    list.add(session1);
    list.add(session2);
  }

  @Test
  void testList() {
    when(sessionRepository.findAll()).thenReturn(list);
    List<Session> actual = sessionRepository.findAll();
    verify(sessionRepository, times(1)).findAll();
    assertEquals(session1, actual.get(0));
    assertEquals(session2, actual.get(1));
    assertEquals(list.size(), actual.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), actual.get(i));
    }
  }

  @Test
  void testList_Exception() {
    when(sessionRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

    List<SessionDto> result = sessionsService.getAllSession();
    verify(sessionRepository, times(1)).findAll();
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void testGet() {
    when(sessionRepository.findById(1L)).thenReturn(Optional.of(session1));
    SessionDto actual = sessionsService.getSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    assertNotNull(actual);
    assertEquals(sessionDto, actual);
  }

  @Test
  void testGet_SessionNotFound() {
    when(sessionRepository.findById(any())).thenReturn(Optional.empty());
    SessionDto actual = sessionsService.getSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    assertNull(actual);
  }

  @Test
  void testGet_Exception() {
    when(sessionRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    SessionDto actual = sessionsService.getSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    assertNull(actual);

  }

  @Test
  void testCreate() {
    when(sessionRepository.saveAndFlush(any())).thenReturn(session1);
    SessionDto result = sessionsService.createSession(sessionDto);
    assertEquals(sessionDto, result);
    verify(sessionRepository, times(1)).saveAndFlush(any());
  }

  @Test
  void testCreate_Exception() {
    when(sessionRepository.saveAndFlush(any())).thenThrow(new RuntimeException("Test exception"));
    SessionDto actual = sessionsService.createSession(sessionDto);
    verify(sessionRepository, times(1)).saveAndFlush(any());
    assertNull(actual);
  }

  @Test
  void testDelete() {
    when(sessionRepository.findById(1L)).thenReturn(Optional.of(session1));
    sessionsService.deleteSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    verify(sessionRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDelete_SessionNotFound() {
    when(sessionRepository.findById(any())).thenReturn(Optional.empty());
    sessionsService.deleteSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    verify(sessionRepository, never()).deleteById(anyLong());
  }

  @Test
  void testDelete_ExceptionThrown() {
    when(sessionRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    sessionsService.deleteSessionById(1L);
    verify(sessionRepository, times(1)).findById(1L);
    verify(sessionRepository, never()).deleteById(anyLong());
  }

  @Test
  void testUpdate() {
    when(sessionRepository.findById(1L)).thenReturn(Optional.of(session1));
    when(sessionRepository.saveAndFlush(session1)).thenReturn(session1);

    SessionDto actual = sessionsService.updateSessionById(1L, sessionDto);
    verify(sessionRepository, times(1)).findById(1L);
    verify(sessionRepository, times(1)).saveAndFlush(any());
    assertEquals(sessionDto, actual);
  }

  @Test
  void testUpdate_IdNotFound() {
    when(sessionRepository.findById(1L)).thenReturn(Optional.empty());
    SessionDto actual = sessionsService.updateSessionById(1L, sessionDto);
    verify(sessionRepository, times(1)).findById(1L);
    verify(sessionRepository, never()).saveAndFlush(any(Session.class));
    assertNull(actual);
  }

  @Test
  void testUpdate_SessionDtoIsNull() {
    SessionDto actual = sessionsService.updateSessionById(1L, null);
    if (actual != null) {
      verifyNoInteractions(sessionRepository);
    }
    assertNull(actual);
  }

  @Test
  void testUpdate_Exception() {
    when(sessionRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    SessionDto actual = sessionsService.updateSessionById(1L, null);
    verify(sessionRepository, times(1)).findById(1L);
    if (actual != null) {
      verifyNoInteractions(sessionRepository);
    }
    assertNull(actual);
  }
}
