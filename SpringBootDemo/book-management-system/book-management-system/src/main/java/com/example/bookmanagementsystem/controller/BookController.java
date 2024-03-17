package com.example.bookmanagementsystem.controller;

import com.example.bookdto.BookDto;
import com.example.bookmanagementsystem.services.BookService;
import java.util.List;
import java.util.Objects;
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
@RequestMapping("ms-book/api/v1/books")
@Slf4j
public class BookController {

  private final BookService bookService;

  @Autowired
  public BookController(BookService bookService) {
    this.bookService = bookService;
  }


  @PostMapping
  public BookDto create(@RequestBody final BookDto bookDto) {
    log.trace("Entering book create..");
    return bookService.createBook(bookDto);
  }

  @GetMapping("/{id}")
  public BookDto get(@PathVariable Long id) {
    log.trace("Entering book get..");
    return bookService.getBookById(id);
  }

  @PutMapping(value = "{id}")
  public BookDto update(@PathVariable Long id, @RequestBody BookDto bookDto) {
    log.trace("entering update()");
    return bookService.updateBook(id, bookDto);

  }

  @DeleteMapping(value = "{id}")
  public void delete(@PathVariable Long id) {
    log.trace("Entering delete() with id: " + id);
    bookService.deleteById(id);
  }

  @GetMapping
  public List<BookDto> list() {
    log.trace("Entering list()");
    return bookService.getBooks();
  }

  public String hello(String language, String name) {
    if (Objects.equals(language, "en")) {
      return "Hello " + name;

    } else if (Objects.equals(language, "ch")) {
      return "Ni Hao " + name;
    } else {
      return name;
    }
  }
}
