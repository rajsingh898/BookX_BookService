package com.book.BookService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Data
public class ExchangeMatchDTO {
    private UUID otherUserId;

    private BookSummaryDTO theyOffer;
    private BookSummaryDTO theyWant;
}
