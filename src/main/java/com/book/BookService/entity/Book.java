package com.book.BookService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(length = 255)
    private String author;

    @Column(length = 100)
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 20)
    private String isbn;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // getters & setters
}
