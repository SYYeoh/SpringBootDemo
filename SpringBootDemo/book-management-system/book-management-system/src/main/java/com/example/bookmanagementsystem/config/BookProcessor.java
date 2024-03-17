package com.example.bookmanagementsystem.config;

import com.example.bookmanagementsystem.model.entity.Book;
import org.springframework.batch.item.ItemProcessor;

public class BookProcessor implements ItemProcessor<Book, Book> {

  @Override
  public Book process(Book book) throws Exception {
    return book;
  }
}
