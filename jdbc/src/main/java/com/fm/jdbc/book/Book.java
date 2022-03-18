package com.fm.jdbc.book;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private String author;
    private LocalDate publishDate;


}
