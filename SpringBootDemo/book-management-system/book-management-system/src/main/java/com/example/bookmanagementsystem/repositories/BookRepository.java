package com.example.bookmanagementsystem.repositories;

import com.example.bookmanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
