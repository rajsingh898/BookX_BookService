package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter@Setter
public class AddWantedBookDTO {
    private UUID userId;

    // Book identity
    private String title;
    private String author;
    private String genre;

    // OwnedBook info


}
