package com.book.BookService.repository;

import com.book.BookService.entity.Book;
import com.book.BookService.entity.OwnedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface OwnedBookRepository extends JpaRepository<OwnedBook, UUID> {
    @Query("SELECT ob.book FROM OwnedBook ob WHERE ob.userId = :userId")
    List<Book> findBooksByUserId(@Param("userId") UUID userId);
    boolean existsByUserIdAndBook(UUID userId, Book book);


}



