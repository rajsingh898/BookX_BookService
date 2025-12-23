package com.book.BookService.service;

import com.book.BookService.dto.RequestBookDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.exception.DuplicateResourceException;
import com.book.BookService.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private BookRepository bookRepository;
    public BookService( BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public String addBook(RequestBookDTO rdt) {
        if (bookRepository.existsByTitleAndAuthorAndGenre(
                rdt.getTitle(), rdt.getAuthor(), rdt.getGenre())) {

            throw new DuplicateResourceException(
                    "Book already exists"
            );
        }

        Book b1 = new Book();
        b1.setAuthor(rdt.getAuthor());
        b1.setIsbn(rdt.getIsbn());
        b1.setDescription(rdt.getDescription());
        b1.setGenre(rdt.getGenre());
        b1.setTitle(rdt.getTitle());
        bookRepository.save(b1);
        return "Book Added";
    }
}
