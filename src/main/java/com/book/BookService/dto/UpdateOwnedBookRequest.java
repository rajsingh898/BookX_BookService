package com.book.BookService.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateOwnedBookRequest {

    private String readStatus;
    private String condition;
    private Boolean exchangeReady;


}
