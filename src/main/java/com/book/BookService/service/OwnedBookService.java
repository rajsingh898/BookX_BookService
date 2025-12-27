package com.book.BookService.service;

import com.book.BookService.dto.AddOwnedBookDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.entity.OwnedBook;
import com.book.BookService.exception.DuplicateResourceException;
import com.book.BookService.repository.BookRepository;
import com.book.BookService.repository.OwnedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OwnedBookService {
    private OwnedBookRepository ownedBookRepository;
    private BookRepository bookRepository;
    public OwnedBookService( OwnedBookRepository ownedBookRepository, BookRepository bookRepository) {
        this.ownedBookRepository = ownedBookRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> ownedBooks(UUID userId) {
        return ownedBookRepository.findBooksByUserId(userId);
    }

    @Transactional
    public void addOwnedBook(AddOwnedBookDTO dto, UUID userId) {

        Book book = bookRepository
                .findByTitleAndAuthorAndGenre(dto.getTitle(), dto.getAuthor(), dto.getGenre())
                .orElseGet(() -> {
                    Book b = new Book();
                    b.setTitle(dto.getTitle());
                    b.setAuthor(dto.getAuthor());
                    b.setGenre(dto.getGenre());
                    return bookRepository.save(b);
                });

        if (ownedBookRepository.existsByUserIdAndBook(userId, book)) {
            throw new DuplicateResourceException(
                    "Owned book already exists for this user"
            );
        }

        OwnedBook ownedBook = new OwnedBook();
        ownedBook.setId(UUID.randomUUID());
        ownedBook.setUserId(userId);
        ownedBook.setBook(book);
        ownedBook.setCondition(dto.getCondition());
        ownedBook.setReadStatus(dto.getReadStatus());

        ownedBookRepository.save(ownedBook);
    }


}
