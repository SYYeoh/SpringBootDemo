package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.services.SessionsService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SessionsControllerTest {

  @InjectMocks
  SessionsController sessionsController;

  @Mock
  SessionsService sessionsService;
  private SessionDto sessionDto;
  private SessionDto sessionDto1;
  private List<SessionDto> DtoList;

  @BeforeEach
  void setUp() {
    sessionDto = new SessionDto(1L, "session1", "session desc 1", 1, null);
    sessionDto1 = new SessionDto(2L, "sessionDto2", "sessionDto desc 2", 2, null);
    DtoList = new ArrayList<>();
    DtoList.add(sessionDto);
    DtoList.add(sessionDto1);
  }

  @Test
  void testList() {
    when(sessionsService.getAllSession()).thenReturn(DtoList);
    List<SessionDto> actual = sessionsController.list();
    verify(sessionsService, times(1)).getAllSession();
    assertEquals(sessionDto, actual.get(0));
    assertEquals(sessionDto1, actual.get(1));
    assertEquals(DtoList.size(), actual.size());
    for (int i = 0; i < DtoList.size(); i++) {
      assertEquals(DtoList.get(i), actual.get(i));
    }
  }

  @Test
  void testGet() {
    when(sessionsService.getSessionById(1L)).thenReturn(sessionDto);
    SessionDto actual = sessionsController.get(1L);
    verify(sessionsService, times(1)).getSessionById(1L);
    assertEquals(sessionDto, actual);
  }

  @Test
  void testCreate() {
    when(sessionsService.createSession(sessionDto)).thenReturn(sessionDto);
    SessionDto actual = sessionsController.create(sessionDto);
    verify(sessionsService, times(1)).createSession(sessionDto);
    assertEquals(sessionDto, actual);
  }

  @Test
  void testDelete() {
    sessionsController.delete(1L);
    verify(sessionsService, times(1)).deleteSessionById(1L);
  }

  @Test
  void testUpdate() {
    when(sessionsService.getSessionById(1L)).thenReturn(sessionDto);
    when(sessionsService.updateSessionById(1L, sessionDto)).thenReturn(sessionDto);

    SessionDto actual = sessionsController.update(1L, sessionDto);
    verify(sessionsService, times(1)).updateSessionById(1L, sessionDto);
  }

}
