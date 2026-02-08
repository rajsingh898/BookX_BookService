package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookSummaryDTO {
    private UUID id;
    private String title;
    private String author;
    private String genre;
    private String description;
}

