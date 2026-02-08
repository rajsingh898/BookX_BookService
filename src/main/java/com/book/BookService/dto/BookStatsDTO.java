package com.book.BookService.dto;

public record BookStatsDTO(
    long owned,
    long read,
    long unread,
    long wanted
    )
{}
