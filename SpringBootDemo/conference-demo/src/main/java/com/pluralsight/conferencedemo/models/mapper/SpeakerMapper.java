package com.pluralsight.conferencedemo.models.mapper;

import com.pluralsight.conferencedemo.models.dto.SpeakerDto;
import com.pluralsight.conferencedemo.models.entity.Speaker;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SpeakerMapper {

  SpeakerMapper INSTANCE = Mappers.getMapper(SpeakerMapper.class);

  SpeakerDto toDto(Speaker speaker);

  Speaker toEntity(SpeakerDto speakerDto);
}
