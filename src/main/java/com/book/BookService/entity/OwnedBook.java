package com.book.BookService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "owned_books",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "book_id"})
)
@Data
public class OwnedBook {

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(nullable = false, length = 20)
    private String condition;

    @Column(name = "read_status", nullable = false, length = 20)
    private String readStatus;

    @Column(name = "exchange_ready", nullable = false)
    private boolean exchangeReady = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    // getters & setters
}
