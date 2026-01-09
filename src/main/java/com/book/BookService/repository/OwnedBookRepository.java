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
    List<Book> findBooksByUserId2(@Param("userId") UUID userId);
    @Query("""
   SELECT ob
   FROM OwnedBook ob
   JOIN FETCH ob.book
   WHERE ob.userId = :userId
""")
    List<OwnedBook> findByUserId(UUID userId);

    boolean existsByUserIdAndBook(UUID userId, Book book);
    List<OwnedBook> findByBookIn(List<Book> books);
    boolean existsByUserIdAndBookIn(UUID userId, List<Book> books);
    boolean existsByBook_IdAndUserId(UUID bookId, UUID userId);

    void deleteByBook_IdAndUserId(UUID bookId, UUID userId);

    Optional<OwnedBook> findByBook_IdAndUserId(UUID bookId, UUID userId);

}



