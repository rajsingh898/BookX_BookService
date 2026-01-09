package com.book.BookService.service;

import com.book.BookService.dto.AddWantedBookDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.entity.WantedBook;
import com.book.BookService.exception.DuplicateResourceException;
import com.book.BookService.repository.BookRepository;
import com.book.BookService.repository.WantedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WantedBookService {

    private final WantedBookRepository wantedBookRepository;
    private final BookRepository bookRepository;

    public WantedBookService(WantedBookRepository wantedBookRepository,
                             BookRepository bookRepository) {
        this.wantedBookRepository = wantedBookRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> getWantedBooks(UUID userid) {
    return wantedBookRepository.findBooksByUserId(userid);
    }

    @Transactional
    public void addWantedBook(AddWantedBookDTO dto, UUID userId) {

        Book book = bookRepository
                .findByTitleAndAuthorAndGenre(dto.getTitle(), dto.getAuthor(), dto.getGenre())
                .orElseGet(() -> {
                    Book b = new Book();
                    b.setTitle(dto.getTitle());
                    b.setAuthor(dto.getAuthor());
                    b.setGenre(dto.getGenre());
                    return bookRepository.save(b);
                });

        if (wantedBookRepository.existsByUserIdAndBook(userId, book)) {
            throw new DuplicateResourceException(
                    "Wanted book already exists for this user"
            );
        }

        WantedBook wantedBook = new WantedBook();
        wantedBook.setUserId(userId);
        wantedBook.setBook(book);

        wantedBookRepository.save(wantedBook);
    }

    @Transactional
    public void removeWantedBook(UUID bookId, UUID userId) {

        if (!wantedBookRepository.existsByBook_IdAndUserId(bookId, userId)) {
            throw new RuntimeException("Wanted book not found for this user");
        }

        wantedBookRepository.deleteByBook_IdAndUserId(bookId, userId);
    }

}
