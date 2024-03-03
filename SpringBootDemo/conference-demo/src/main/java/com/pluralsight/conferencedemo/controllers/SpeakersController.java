package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
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
@RequestMapping("/api/v1/speakers")
@Slf4j
public class SpeakersController {

  @Autowired
  private SpeakerRepository speakerRepository;

  @GetMapping
  public List<Speaker> list() {
    log.trace("Entering list()");
    return speakerRepository.findAll();
  }

  @GetMapping
  @RequestMapping("{id}")
  public Speaker get(@PathVariable Long id) {
    log.trace("get speaker id: " + id);
    return speakerRepository.getOne(id);
  }

  @PostMapping
  public Speaker create(@RequestBody Speaker speaker) {
    log.trace("Creating firstName: " + speaker.getFirstName());
    log.trace("Creating lastName : " + speaker.getLastName());
    log.trace("Creating title : " + speaker.getTitle());
    log.trace("Creating company : " + speaker.getCompany());
    log.trace("Creating speakerBio : " + speaker.getSpeakerBio());
    return speakerRepository.saveAndFlush(speaker);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id) {
    //Also need to check for children records before deleting.
    log.trace("Entering delete() with id: " + id);
    speakerRepository.deleteById(id);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
    //because this is a PUT, we expect all attributes to be passed in.
    //A PATCH would only need what has changed.

    //TODO: Add validation that all attributes are passed in,
    //otherwise return a 400 bad payload

    log.trace("entering update()");
    log.trace("firstName: " + speaker.getFirstName());
    log.trace("lastName: " + speaker.getLastName());
    log.trace("title: " + speaker.getTitle());
    log.trace("company: " + speaker.getCompany());
    log.trace("speakerBio: " + speaker.getSpeakerBio());
    Speaker existingSpeaker = speakerRepository.getOne(id);
    BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
    return speakerRepository.saveAndFlush(existingSpeaker);
  }

}
