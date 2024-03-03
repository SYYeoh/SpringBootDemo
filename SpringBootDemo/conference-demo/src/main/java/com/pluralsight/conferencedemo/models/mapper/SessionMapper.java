package com.pluralsight.conferencedemo.models.mapper;

import com.pluralsight.conferencedemo.models.dto.SessionDto;
import com.pluralsight.conferencedemo.models.entity.Session;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SessionMapper {

  SessionMapper INSTANCE = Mappers.getMapper(SessionMapper.class);

  SessionDto toDto(Session session);

  Session toEntity(SessionDto sessionDto);
}
