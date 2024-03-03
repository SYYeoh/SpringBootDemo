package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.dto.SpeakerDto;
import com.pluralsight.conferencedemo.services.SpeakersService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms-conference/api/v1/speakers")
@Slf4j
public class SpeakersController {

  private final SpeakersService speakersService;

  @Autowired
  public SpeakersController(SpeakersService speakersService) {
    this.speakersService = speakersService;
  }

  @GetMapping
  public List<SpeakerDto> list() {
    log.trace("Entering list()");
    return speakersService.getAllSpeaker();
  }

  @GetMapping("{id}")
  public SpeakerDto get(@PathVariable Long id) {
    log.trace("get speaker id: " + id);
    return speakersService.getSpeakerById(id);
  }

  @PostMapping
  public SpeakerDto create(@RequestBody SpeakerDto speakerDto) {
    log.trace("Entering Speakers Create..");
    return speakersService.createSpeaker(speakerDto);
  }

  @DeleteMapping(value = "{id}")
  public void delete(@PathVariable Long id) {
    //Also need to check for children records before deleting.
    log.trace("Entering delete() with id: " + id);
    speakersService.deleteSpeakerById(id);
  }

  @PutMapping(value = "{id}")
  public SpeakerDto update(@PathVariable Long id, @RequestBody SpeakerDto speakerDto) {
    //because this is a PUT, we expect all attributes to be passed in.
    //A PATCH would only need what has changed.
    log.trace("entering update()");
    return speakersService.updateSpeakerById(id, speakerDto);
  }

}
