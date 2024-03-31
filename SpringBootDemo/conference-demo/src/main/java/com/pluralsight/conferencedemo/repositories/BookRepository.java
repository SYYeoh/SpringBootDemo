package com.pluralsight.conferencedemo.repositories;

import com.pluralsight.conferencedemo.models.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
