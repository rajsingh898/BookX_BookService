package com.book.BookService.dto;

import java.util.UUID;

public record MyOwnedBookDto(
        UUID id,              // book id
        String title,
        String author,
        String genre,
        String readStatus,
        String condition,
        boolean exchangeReady
) {}

