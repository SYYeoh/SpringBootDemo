package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.repositories.BookRepository;
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
@RequestMapping("/api/v1/books")
@Slf4j
public class BookController {
  @Autowired
  private BookRepository bookRepository;

  @PostMapping
  public Book create(@RequestBody final Book book) {
    log.trace("Creating Book title: " + book.getTitle());
    log.trace("Creating Book author: " + book.getAuthor());
    log.trace("Creating Book publication year: " + book.getPublicationYear());
    return bookRepository.saveAndFlush(book);
  }

  @GetMapping
  @RequestMapping("{id}")
  public Book get(@PathVariable Long id) {
    log.trace("get book id: " + id);
    return bookRepository.getReferenceById(id);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.PUT)
  public Book update(@PathVariable Long id, @RequestBody Book book) {
    log.trace("entering update()");
    log.trace("id: " + id);
    log.trace("book: " + book);
    Book existingBook = bookRepository.getReferenceById(id);
    log.debug("ExistingBook: " + existingBook);
    BeanUtils.copyProperties(book, existingBook, "id");
    log.debug("Updated book: " + existingBook);
    return bookRepository.saveAndFlush(existingBook);
  }

  @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
  public void delete(@PathVariable Long id) {
    log.trace("Entering delete() with id: " + id);
    bookRepository.deleteById(id);
  }

  @GetMapping
  public List<Book> list() {
    log.trace("Entering list()");
    return bookRepository.findAll();
  }
}
