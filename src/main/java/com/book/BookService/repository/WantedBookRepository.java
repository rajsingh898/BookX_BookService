package com.book.BookService.repository;

import com.book.BookService.entity.Book;
import com.book.BookService.entity.WantedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface WantedBookRepository extends JpaRepository<WantedBook, UUID> {
    @Query("SELECT wb.book FROM WantedBook wb where wb.userId=:userId")
    List<Book> findBooksByUserId(@Param("userId") UUID userId);
    boolean existsByUserIdAndBook(UUID userId, Book book);
    List<WantedBook> findByUserId(UUID userId);

    List<WantedBook> findByBookIn(List<Book> books);

    boolean existsByUserIdAndBookIn(UUID userId, List<Book> books);
    boolean existsByBook_IdAndUserId(UUID bookId, UUID userId);

    void deleteByBook_IdAndUserId(UUID bookId, UUID userId);


}
