package com.example.bookmanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

  private Long id;
  private String title;
  private String author;
  private Integer publicationYear;
}
