package com.pluralsight.conferencedemo.services;

import com.example.bookdto.BookDto;
import com.pluralsight.conferencedemo.models.entity.Book;
import com.pluralsight.conferencedemo.models.mapper.BookMapper;
import com.pluralsight.conferencedemo.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BooksService {

  private static final String EXCEPTION_ERROR_STRING = "Error delete book failed with id: {} error message: {}";
  private final BookRepository bookRepository;

  @Autowired
  public BooksService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public Book createBook(Book book) {
    try {
      return bookRepository.saveAndFlush(book);
    } catch (Exception e) {
      log.error(EXCEPTION_ERROR_STRING, book.getId(), e.getMessage());
      return null;
    }
  }
}
