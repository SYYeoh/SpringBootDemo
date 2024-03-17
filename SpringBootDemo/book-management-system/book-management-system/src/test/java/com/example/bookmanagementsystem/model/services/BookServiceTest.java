package com.example.bookmanagementsystem.model.services;

import com.example.bookdto.BookDto;
import com.example.bookmanagementsystem.model.entity.Book;
import com.example.bookmanagementsystem.repositories.BookRepository;
import com.example.bookmanagementsystem.services.BookService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

  @InjectMocks
  BookService bookService;

  @Mock
  BookRepository bookRepository;
  private Book book;
  private Book book1;
  private BookDto bookDto;
  private List<Book> books;

  @BeforeEach
  void setUp() {
    bookDto = new BookDto(1L, "Title 1", "Author 1", 2001);

    book = new Book(1L, "Title 1", "Author 1", 2001);
    book1 = new Book(2L, "Title 2", "Author 2", 2002);
    books = new ArrayList<>();
    books.add(book);
    books.add(book1);
  }

  @Test
  void testCreateBook() {
    when(bookRepository.saveAndFlush(any())).thenReturn(book);
    BookDto actual = bookService.createBook(bookDto);
    assertEquals(bookDto, actual);
    verify(bookRepository, times(1)).saveAndFlush(any());
  }

  @Test
  void testCreate_Exception() {
    when(bookRepository.saveAndFlush(any())).thenThrow(new RuntimeException("Test exception"));
    BookDto actual = bookService.createBook(bookDto);
    verify(bookRepository, times(1)).saveAndFlush(any());
    assertNull(actual);
  }

  @Test
  void testGet() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    BookDto actual = bookService.getBookById(1L);
    verify(bookRepository, times(1)).findById(1L);
    assertNotNull(actual);
    assertEquals(bookDto, actual);
  }

  @Test
  void testGet_IdNotFound() {
    when(bookRepository.findById(any())).thenReturn(Optional.empty());
    BookDto actual = bookService.getBookById(1L);
    verify(bookRepository, times(1)).findById(1L);
    assertNull(actual);
  }

  @Test
  void testGet_Exception() {
    when(bookRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    BookDto actual = bookService.getBookById(1L);
    verify(bookRepository, times(1)).findById(1L);
    assertNull(actual);

  }

  @Test
  void testUpdate() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    when(bookRepository.saveAndFlush(book)).thenReturn(book);

    BookDto actual = bookService.updateBook(1L, bookDto);
    verify(bookRepository, times(1)).findById(1L);
    verify(bookRepository, times(1)).saveAndFlush(any());
    assertEquals(bookDto, actual);
  }

  @Test
  void testUpdate_IdNotFound() {
    when(bookRepository.findById(1L)).thenReturn(Optional.empty());
    BookDto actual = bookService.updateBook(1L, bookDto);
    verify(bookRepository, times(1)).findById(1L);
    verify(bookRepository, never()).saveAndFlush(any(Book.class));
    assertNull(actual);
  }

  @Test
  void testUpdate_BookDtoIsNull() {
    BookDto actual = bookService.updateBook(1L, null);
    if (actual != null) {
      verifyNoInteractions(bookRepository);
    }
    assertNull(actual);
  }

  @Test
  void testUpdate_Exception() {
    when(bookRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    BookDto actual = bookService.updateBook(1L, null);
    if (actual != null) {
      verifyNoInteractions(bookRepository);
    }
    assertNull(actual);
  }

  @Test
  void testDelete() {
    when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
    bookService.deleteById(1L);
    verify(bookRepository, times(1)).findById(1L);
    verify(bookRepository, times(1)).deleteById(1L);
  }

  @Test
  void testDelete_SessionNotFound() {
    when(bookRepository.findById(any())).thenReturn(Optional.empty());
    bookService.deleteById(1L);
    verify(bookRepository, times(1)).findById(1L);
    verify(bookRepository, never()).deleteById(anyLong());
  }

  @Test
  void testDelete_ExceptionThrown() {
    when(bookRepository.findById(1L)).thenThrow(new RuntimeException("Test exception"));
    bookService.deleteById(1L);
    verify(bookRepository, times(1)).findById(1L);
    verify(bookRepository, never()).deleteById(anyLong());
  }

  @Test
  void testList() {
    when(bookRepository.findAll()).thenReturn(books);
    List<Book> actual = bookRepository.findAll();
    verify(bookRepository, times(1)).findAll();
    assertEquals(book, actual.get(0));
    assertEquals(book1, actual.get(1));
    assertEquals(books.size(), actual.size());
    for (int i = 0; i < books.size(); i++) {
      assertEquals(books.get(i), actual.get(i));
    }
  }

  @Test
  void testList_Exception() {
    when(bookRepository.findAll()).thenThrow(new RuntimeException("Test exception"));

    List<BookDto> result = bookService.getBooks();
    verify(bookRepository, times(1)).findAll();
    assertEquals(Collections.emptyList(), result);
  }
}
