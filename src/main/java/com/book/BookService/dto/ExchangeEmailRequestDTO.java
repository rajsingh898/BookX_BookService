package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExchangeEmailRequestDTO {

    private String myName;
    private String myEmail;

    private String otherUserName;
    private String otherUserEmail;

    private BookSummaryDTO myBook;
    private BookSummaryDTO theirBook;
    private String theirBookCondition;

 }

