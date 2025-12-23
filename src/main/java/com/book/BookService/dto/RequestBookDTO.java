package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestBookDTO {
    private String title;
    private String author;
    private String genre;
    private String description;
    private String isbn;

}
