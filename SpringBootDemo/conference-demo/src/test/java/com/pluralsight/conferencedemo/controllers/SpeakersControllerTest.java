package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.dto.SpeakerDto;
import com.pluralsight.conferencedemo.services.SpeakersService;
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
class SpeakersControllerTest {

  @InjectMocks
  SpeakersController speakersController;

  @Mock
  SpeakersService speakersService;
  private SpeakerDto speakerDto;
  private SpeakerDto speakerDto1;
  private List<SpeakerDto> DtoList;

  @BeforeEach
  void setUp() {
    speakerDto = new SpeakerDto(1L, "first1", "last1", "title1", "company1", "bio1", null, null);
    speakerDto1 = new SpeakerDto(2L, "first2", "last2", "title2", "company2", "bio2", null, null);
    DtoList = new ArrayList<>();
    DtoList.add(speakerDto);
    DtoList.add(speakerDto1);
  }

  @Test
  void testList() {
    when(speakersService.getAllSpeaker()).thenReturn(DtoList);
    List<SpeakerDto> actual = speakersController.list();
    verify(speakersService, times(1)).getAllSpeaker();
    assertEquals(speakerDto, actual.get(0));
    assertEquals(speakerDto1, actual.get(1));
    assertEquals(DtoList.size(), actual.size());
    for (int i = 0; i < DtoList.size(); i++) {
      assertEquals(DtoList.get(i), actual.get(i));
    }
  }

  @Test
  void testGet() {
    when(speakersService.getSpeakerById(1L)).thenReturn(speakerDto);
    SpeakerDto actual = speakersController.get(1L);
    verify(speakersService, times(1)).getSpeakerById(1L);
    assertEquals(speakerDto, actual);
  }

  @Test
  void testCreate() {
    when(speakersService.createSpeaker(speakerDto)).thenReturn(speakerDto);
    SpeakerDto actual = speakersController.create(speakerDto);
    verify(speakersService, times(1)).createSpeaker(speakerDto);
    assertEquals(speakerDto, actual);
  }

  @Test
  void testDelete() {
    speakersController.delete(1L);
    verify(speakersService, times(1)).deleteSpeakerById(1L);
  }

  @Test
  void testUpdate() {
    when(speakersService.getSpeakerById(1L)).thenReturn(speakerDto);
    when(speakersService.updateSpeakerById(1L, speakerDto)).thenReturn(speakerDto);

    SpeakerDto actual = speakersController.update(1L, speakerDto);
    verify(speakersService, times(1)).updateSpeakerById(1L, speakerDto);
  }
}
