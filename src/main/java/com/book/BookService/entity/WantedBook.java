package com.book.BookService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "wanted_books",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"})
)
@Getter@Setter
public class WantedBook {

    @Id
    @Column(nullable = false)
    @GeneratedValue
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    // getters & setters
}
