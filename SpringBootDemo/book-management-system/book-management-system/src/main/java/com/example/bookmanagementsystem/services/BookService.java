package com.example.bookmanagementsystem.services;

import com.example.bookdto.BookDto;
import com.example.bookmanagementsystem.model.entity.Book;
import com.example.bookmanagementsystem.model.mapper.BookMapper;
import com.example.bookmanagementsystem.repositories.BookRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookService {

  private static final int MAX_BOOKS_THRESHOLD = 100000;
  private static final String EXCEPTION_ERROR_STRING = "Error delete book failed with id: {} error message: {}";
  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  public BookDto createBook(BookDto bookDto) {
    log.trace("Creating Book title: " + bookDto.getTitle());
    log.trace("Creating Book author: " + bookDto.getAuthor());
    log.trace("Creating Book publication year: " + bookDto.getPublicationYear());

    try {
      Book bookEntity = BookMapper.INSTANCE.toEntity(bookDto);
      Book savedBook = bookRepository.saveAndFlush(bookEntity);
      return BookMapper.INSTANCE.toDto(savedBook);
    } catch (Exception e) {
      log.error(EXCEPTION_ERROR_STRING, bookDto.getId(), e.getMessage());
      return null;
    }
  }

  public BookDto getBookById(Long id) {
    log.trace("get book id: " + id);
    try {
      Optional<Book> optionalBook = bookRepository.findById(id);
      if (optionalBook.isPresent()) {
        log.debug("optionalBook present");
        Book book = optionalBook.get();
        return BookMapper.INSTANCE.toDto(book);
      } else {
        log.debug("optionalBook not present");
        return null;
      }
    } catch (Exception e) {
      log.error("Error getBookById() with id: " + id);
      return null;
    }
  }

  public BookDto updateBook(Long id, BookDto bookDto) {
    log.debug("id: " + id);
    log.debug("book: " + bookDto);
    if (null == bookDto) {
      log.debug("bookDto is null");
      return null;
    }
    try {
      Optional<Book> optionalBook = bookRepository.findById(id);
      if (optionalBook.isPresent()) {
        Book book = optionalBook.get();
        Book bookToUpdate = BookMapper.INSTANCE.toEntity(bookDto);
        BeanUtils.copyProperties(bookToUpdate, book, "id");
        Book savedBook = bookRepository.saveAndFlush(book);
        return BookMapper.INSTANCE.toDto(savedBook);
      } else {
        log.debug("Update book failed with " + id);
        log.debug("bookDto: " + bookDto);
        return null;
      }
    } catch (Exception e) {
      log.error(EXCEPTION_ERROR_STRING, id, e.getMessage());
      log.error("bookDto: " + bookDto);
      return null;
    }
  }

  public void deleteById(Long id) {
    try {
      Optional<Book> optionalBook = bookRepository.findById(id);
      if (optionalBook.isPresent()) {
        log.debug("optionalBook present, deleting..");
        bookRepository.deleteById(id);
      } else {
        log.debug("optionalBook not present");
      }
    } catch (Exception e) {
      log.error(EXCEPTION_ERROR_STRING, id, e.getMessage());
    }
  }

  public List<BookDto> getBooks() {
    try {
      List<Book> bookList = bookRepository.findAll();
      if (bookList.size() > MAX_BOOKS_THRESHOLD) {
        log.warn("Returned book list is large. Total books: " + bookList.size());
      }
      return bookList.stream().map(BookMapper.INSTANCE::toDto).toList();
    } catch (Exception e) {
      return Collections.emptyList();
    }
  }
}
