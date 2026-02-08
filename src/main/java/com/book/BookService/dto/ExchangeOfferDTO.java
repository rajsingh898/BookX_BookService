package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExchangeOfferDTO {

    private UUID otherUserId;
    private String otherUserName;

     private BookSummaryDTO myBook;

     private BookSummaryDTO theirBook;

     private String theirBookCondition;
}

