package com.example.bookmanagementsystem.model.mapper;

import com.example.bookdto.BookDto;
import com.example.bookmanagementsystem.model.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

  BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

  BookDto toDto(Book book);

  Book toEntity(BookDto bookDto);
}
