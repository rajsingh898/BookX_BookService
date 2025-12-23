package com.book.BookService.exception;

public class DuplicateResourceException extends ApiException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
