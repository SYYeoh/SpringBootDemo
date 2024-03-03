package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.model.dto.BookDto;
import com.example.bookmanagementsystem.model.entity.Book;
import com.example.bookmanagementsystem.repositories.BookRepository;
import com.example.bookmanagementsystem.services.BookService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {

  @InjectMocks
  BookController bookController;

  @Mock
  BookService bookService;

  private BookDto bookDto;
  private BookDto bookDto1;
  private List<BookDto> books;

  @BeforeEach
  void setUp() {
    bookDto = new BookDto(1L, "Title 1", "Author 1", 2001);
    bookDto1 = new BookDto(2L,"Title 2", "Author 2", 2002);
    books = new ArrayList<>();
    books.add(bookDto);
    books.add(bookDto1);
  }

  @Test
  void testCreate() {
    when(bookService.createBook(bookDto)).thenReturn(bookDto);
    BookDto actual = bookController.create(bookDto);
    verify(bookService, times(1)).createBook(bookDto);
    assertEquals(bookDto, actual);
  }

  @Test
  void testGet() {
    when(bookService.getBookById(1L)).thenReturn(bookDto);
    BookDto actual = bookController.get(1L);
    verify(bookService, times(1)).getBookById(1L);
    assertEquals(bookDto, actual);
  }

  @Test
  void testUpdate() {
    when(bookService.getBookById(1L)).thenReturn(bookDto);
    when(bookService.updateBook(1L, bookDto)).thenReturn(bookDto);

    BookDto actual = bookController.update(1L, bookDto);
    verify(bookService, times(1)).updateBook(1L, bookDto);
  }

  @Test
  void testDelete() {
      bookController.delete(1L);
      verify(bookService, times(1)).deleteById(1L);
  }

  @Test
  void testList() {
    when(bookService.getBooks()).thenReturn(books);
    List<BookDto> actualBooks = bookController.list();
    verify(bookService, times(1)).getBooks();
    assertEquals(bookDto, actualBooks.get(0));
    assertEquals(bookDto1, actualBooks.get(1));
    assertEquals(books.size(), actualBooks.size());
    for (int i = 0; i < books.size(); i++) {
      assertEquals(books.get(i), actualBooks.get(i));
    }
  }

  @Test
  void testHello() {
    String responseEn = bookController.hello("en", "John");
    String responseCh = bookController.hello("ch", "Zhao");
    String response = bookController.hello("", "Ada");
    String expectedEn = "Hello John";
    String expectedCh = "Ni Hao Zhao";
    String expected = "Ada";
    assertEquals(expectedEn, responseEn);
    assertEquals(expectedCh, responseCh);
    assertEquals(expected, response);
  }

}
