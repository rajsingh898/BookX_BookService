package com.book.BookService.service;

import com.book.BookService.dto.*;
import com.book.BookService.entity.Book;
import com.book.BookService.entity.OwnedBook;
import com.book.BookService.exception.DuplicateResourceException;
import com.book.BookService.repository.BookRepository;
import com.book.BookService.repository.OwnedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class OwnedBookService {
    private OwnedBookRepository ownedBookRepository;
    private BookRepository bookRepository;

    public OwnedBookService(OwnedBookRepository ownedBookRepository, BookRepository bookRepository) {
        this.ownedBookRepository = ownedBookRepository;
        this.bookRepository = bookRepository;
    }

    public List<MyOwnedBookDto> ownedBooks(UUID userId) {
        return ownedBookRepository.findByUserId(userId)
                .stream()
                .map(ob -> new MyOwnedBookDto(
                        ob.getBook().getId(),
                        ob.getBook().getTitle(),
                        ob.getBook().getAuthor(),
                        ob.getBook().getGenre(),
                        ob.getReadStatus(),
                        ob.getCondition(),
                        ob.isExchangeReady()
                ))
                .toList();
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

    @Transactional
    public void removeOwnedBook(UUID bookId, UUID userId) {

        if (!ownedBookRepository.existsByBook_IdAndUserId(bookId, userId)) {
            throw new RuntimeException("Wanted book not found for this user");
        }

        ownedBookRepository.deleteByBook_IdAndUserId(bookId, userId);
    }

    @Transactional
    public MyOwnedBookDto updateOwnedBook(
            UUID bookId,
            UUID userId,
            UpdateOwnedBookRequest request
    ) {
        OwnedBook ownedBook = ownedBookRepository
                .findByBook_IdAndUserId(bookId, userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Owned book not found"
                ));

        if (request.getReadStatus() != null) {
            ownedBook.setReadStatus(request.getReadStatus());
        }
        if (request.getCondition() != null) {
            ownedBook.setCondition(request.getCondition());
        }
        if (request.getExchangeReady() != null) {
            ownedBook.setExchangeReady(request.getExchangeReady());
        }

        return new MyOwnedBookDto(
                ownedBook.getBook().getId(),
                ownedBook.getBook().getTitle(),
                ownedBook.getBook().getAuthor(),
                ownedBook.getBook().getGenre(),
                ownedBook.getReadStatus(),
                ownedBook.getCondition(),
                ownedBook.isExchangeReady()
        );
    }


    private OwnedBookDto mapToDto(OwnedBook ownedBook) {
        return new OwnedBookDto(
                ownedBook.getBook().getId(),
                ownedBook.getReadStatus(),
                ownedBook.getCondition(),
                ownedBook.isExchangeReady()
        );
    }

}
