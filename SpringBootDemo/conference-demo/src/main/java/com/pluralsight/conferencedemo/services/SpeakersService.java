package com.pluralsight.conferencedemo.services;

import com.pluralsight.conferencedemo.models.dto.SpeakerDto;
import com.pluralsight.conferencedemo.models.entity.Speaker;
import com.pluralsight.conferencedemo.models.mapper.SpeakerMapper;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SpeakersService {

  private final SpeakerRepository speakerRepository;

  @Autowired
  public SpeakersService(SpeakerRepository speakerRepository) {
    this.speakerRepository = speakerRepository;
  }

  public List<SpeakerDto> getAllSpeaker() {
    try {
      List<Speaker> speakerList = speakerRepository.findAll();
      return speakerList.stream().map(SpeakerMapper.INSTANCE::toDto).toList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }

  public SpeakerDto getSpeakerById(Long id) {
    try {
      Optional<Speaker> speakerOptional = speakerRepository.findById(id);
      if (speakerOptional.isPresent()) {
        Speaker speaker = speakerOptional.get();
        return SpeakerMapper.INSTANCE.toDto(speaker);
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }

  public SpeakerDto createSpeaker(SpeakerDto speakerDto) {
    log.trace("Creating firstName: " + speakerDto.getFirstName());
    log.trace("Creating lastName : " + speakerDto.getLastName());
    log.trace("Creating title : " + speakerDto.getTitle());
    log.trace("Creating company : " + speakerDto.getCompany());
    log.trace("Creating speakerBio : " + speakerDto.getSpeakerBio());
    try {
      Speaker speakerEntity = SpeakerMapper.INSTANCE.toEntity(speakerDto);
      Speaker saveEntity = speakerRepository.saveAndFlush(speakerEntity);
      return SpeakerMapper.INSTANCE.toDto(saveEntity);
    } catch (Exception e) {
      log.error("Error when saving speaker: " + e.getMessage());
      return null;
    }
  }

  public void deleteSpeakerById(Long id) {
    try {
      Optional<Speaker> speakerOptional = speakerRepository.findById(id);
      if (speakerOptional.isPresent()) {
        speakerRepository.deleteById(id);
      } else {
        log.debug("enter deleteSpeakerById speakerOptional.isPresent() ELSE");
      }
    } catch (Exception e) {
      log.error("error deleting speaker with id " + id + ": " + e.getMessage());
    }
  }

  public SpeakerDto updateSpeakerById(Long id, SpeakerDto speakerDto) {
    if (null == speakerDto) {
      log.debug("speakerDto is null");
    }
    try {
      Optional<Speaker> speakerOptional = speakerRepository.findById(id);
      if (speakerOptional.isPresent()) {
        Speaker existingSpeaker = speakerOptional.get();
        Speaker speakerToUpdate = SpeakerMapper.INSTANCE.toEntity(speakerDto);
        BeanUtils.copyProperties(speakerToUpdate, existingSpeaker, "speakerId");
        Speaker speakerUpdated = speakerRepository.saveAndFlush(speakerToUpdate);
        return SpeakerMapper.INSTANCE.toDto(speakerUpdated);
      } else {
        log.debug("Update Speaker fail with id " + id);
        log.debug("speakerId: " + speakerDto.getSpeakerId());
        log.debug("title: " + speakerDto.getTitle());
        log.debug("company: " + speakerDto.getCompany());
        log.debug("speakerBio: " + speakerDto.getSpeakerBio());
        return null;
      }
    } catch (Exception e) {
      log.error("error update speaker with id " + id + ": " + e.getMessage());
      return null;
    }
  }

}
