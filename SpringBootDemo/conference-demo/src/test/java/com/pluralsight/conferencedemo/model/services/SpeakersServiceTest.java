package com.pluralsight.conferencedemo.model.services;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.models.dto.SpeakerDto;
import com.pluralsight.conferencedemo.models.entity.Speaker;
import com.pluralsight.conferencedemo.models.mapper.SpeakerMapper;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import com.pluralsight.conferencedemo.services.SpeakersService;
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
class SpeakersServiceTest {

  @InjectMocks
  SpeakersService speakersService;

  @Mock
  SpeakerRepository speakerRepository;
  private Speaker speaker;
  private Speaker speaker1;
  private SpeakerDto speakerDto;
  private List<Speaker> list;

  @BeforeEach
  void setUp() {
    speakerDto = new SpeakerDto(1L, "first1", "last1", "title1", "company1", "bio1", null, null);

    speaker = new Speaker(1L, "first1", "last1", "title1", "company1", "bio1", null, null);
    speaker1 = new Speaker(2L, "first2", "last2", "title2", "company2", "bio2", null, null);
    list = new ArrayList<>();
    list.add(speaker);
    list.add(speaker1);
  }

  @Test
  void testList() {
    when(speakerRepository.findAll()).thenReturn(list);
    List<Speaker> actual = speakerRepository.findAll();
    verify(speakerRepository, times(1)).findAll();
    assertEquals(speaker, actual.get(0));
    assertEquals(speaker1, actual.get(1));
    assertEquals(list.size(), actual.size());
    for (int i = 0; i < list.size(); i++) {
      assertEquals(list.get(i), actual.get(i));
    }
  }

  @Test
  void testList_Exception() {
    when(speakerRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

    List<SpeakerDto> result = speakersService.getAllSpeaker();
    verify(speakerRepository, times(1)).findAll();
    assertEquals(Collections.emptyList(), result);
  }

  @Test
  void testGet() {
    when(speakerRepository.findById(1L)).thenReturn(Optional.of(speaker));
    SpeakerDto actual = speakersService.getSpeakerById(1L);
    verify(speakerRepository, times(1)).findById(1L);
    assertNotNull(actual);
    assertEquals(speakerDto, actual);
  }

  @Test
  void testGet_SpeakerNotFound() {
   when(speakerRepository.findById(any())).thenReturn(Optional.empty());
   SpeakerDto actual = speakersService.getSpeakerById(1L);
   verify(speakerRepository, times(1)).findById(1L);
   assertNull(actual);
  }

  @Test
  void testGet_Exception() {
    when(speakerRepository.findById(any())).thenThrow(new RuntimeException("Test exception"));
    SpeakerDto actual = speakersService.getSpeakerById(1L);
    verify(speakerRepository, times(1)).findById(1L);
    assertNull(actual);
  }

  @Test
  void testCreate() {
    when(speakerRepository.saveAndFlush(any())).thenReturn(speaker);
    SpeakerDto actual = speakersService.createSpeaker(speakerDto);
    assertEquals(speakerDto, actual);
    verify(speakerRepository, times(1)).saveAndFlush(any());
  }

  @Test
  void testCreate_Exception() {
    when(speakerRepository.saveAndFlush(any())).thenThrow(new RuntimeException("Test exception"));
    SpeakerDto actual = speakersService.createSpeaker(speakerDto);
    verify(speakerRepository, times(1)).saveAndFlush(any());
    assertNull(actual);
  }

  @Test
  void testDelete() {
    when(speakerRepository.findById(1L)).thenReturn(Optional.of(speaker));
    speakersService.deleteSpeakerById(1L);
    verify(speakerRepository, times(1)).findById(1L);
    verify(speakerRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDelete_SpeakerNotFound(){
    when(speakerRepository.findById(1L)).thenReturn(Optional.empty());
    speakersService.deleteSpeakerById(1L);
    verify(speakerRepository, times(1)).findById(1L);
    verify(speakerRepository, never()).deleteById(anyLong());
  }

  @Test
  void testDelete_ExceptionThrown() {
    when(speakerRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    speakersService.deleteSpeakerById(1L);
    verify(speakerRepository, times(1)).findById(1L);
    verify(speakerRepository, never()).deleteById(anyLong());
  }

  @Test
  void testUpdate() {
    when(speakerRepository.findById(1L)).thenReturn(Optional.of(speaker));
    when(speakerRepository.saveAndFlush(speaker)).thenReturn(speaker);

    SpeakerDto actual = speakersService.updateSpeakerById(1L, speakerDto);
    verify(speakerRepository, times(1)).findById(1L);
    verify(speakerRepository, times(1)).saveAndFlush(any());
  }

  @Test
  void testUpdate_IdNotFound() {
    when(speakerRepository.findById(1L)).thenReturn(Optional.empty());
    SpeakerDto actual = speakersService.updateSpeakerById(1L, speakerDto);
    verify(speakerRepository, times(1)).findById(1L);
    verify(speakerRepository, never()).saveAndFlush(any(Speaker.class));
    assertNull(actual);
  }

  @Test
  void testUpdate_SpeakerDtoIsNull() {
    SpeakerDto actual = speakersService.updateSpeakerById(1L, null);
    if (actual != null) {
      verifyNoInteractions(speakerRepository);
    }
    assertNull(actual);
  }
  @Test
  void testUpdate_Exception() {
    when(speakerRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    SpeakerDto actual = speakersService.updateSpeakerById(1L, null);
    verify(speakerRepository, times(1)).findById(1L);
    if (actual != null) {
      verifyNoInteractions(speakerRepository);
    }
    assertNull(actual);
  }

}
