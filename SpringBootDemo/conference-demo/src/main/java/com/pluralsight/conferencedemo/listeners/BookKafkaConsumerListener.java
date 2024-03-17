package com.pluralsight.conferencedemo.listeners;

import com.example.bookdto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralsight.conferencedemo.models.entity.Book;
import com.pluralsight.conferencedemo.models.mapper.BookMapper;
import com.pluralsight.conferencedemo.services.BooksService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BookKafkaConsumerListener {

  private final BooksService booksService;
  private final ObjectMapper objectMapper;

  public BookKafkaConsumerListener(BooksService booksService, ObjectMapper objectMapper) {
    this.booksService = booksService;
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "book-data", groupId = "consumer-1")
  public void insertBookData(String msg) {
    try {
      log.info("Kafka message: " + msg);
      BookDto bookDto = objectMapper.readValue(msg, BookDto.class);
      Book bookEntity = BookMapper.INSTANCE.toEntity(bookDto);
      booksService.createBook(bookEntity);
    } catch (JsonProcessingException e) {
      log.error("InsertBookData from Kafka error: " + msg);
    }
  }
}
