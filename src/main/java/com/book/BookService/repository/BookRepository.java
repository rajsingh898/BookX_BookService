package com.book.BookService.repository;

import com.book.BookService.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository <Book, UUID> {
    Optional<Book> findByTitleAndAuthorAndGenre(
            String title,
            String author,
            String genre
    );
    boolean existsByTitleAndAuthorAndGenre(String title, String author, String genre);

}
