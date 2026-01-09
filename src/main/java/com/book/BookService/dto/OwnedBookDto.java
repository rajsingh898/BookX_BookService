package com.book.BookService.dto;

import java.util.UUID;

public record OwnedBookDto(
        UUID id,
        String readStatus,
        String condition,
        Boolean exchangeReady
) {}

