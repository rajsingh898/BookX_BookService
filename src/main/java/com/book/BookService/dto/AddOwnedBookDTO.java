package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter@Setter
public class AddOwnedBookDTO {
    private UUID userId;

    // Book identity
    private String title;
    private String author;
    private String genre;

    // OwnedBook info
    private String condition;
    private String readStatus;
    private Boolean exchangeReady;
}
