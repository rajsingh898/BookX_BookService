package com.book.BookService.contoller;

import com.book.BookService.dto.AddOwnedBookDTO;
import com.book.BookService.dto.AddWantedBookDTO;
import com.book.BookService.dto.RequestBookDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.service.BookService;
import com.book.BookService.service.OwnedBookService;
import com.book.BookService.service.WantedBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/books")
public class BookController {
    private BookService bookService;
    private OwnedBookService ownedBookService;
    private WantedBookService wantedBookService;
    public BookController(BookService bookService,OwnedBookService ownedBookService,WantedBookService wantedBookService){
        this.bookService= bookService;
        this.ownedBookService = ownedBookService;
        this.wantedBookService=wantedBookService;
    }

    @GetMapping("/all")
    public List<Book> allBooks(){
        return bookService.getAllBooks();
    }
    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody RequestBookDTO rdt) {
        bookService.addBook(rdt);
        return ResponseEntity.status(HttpStatus.CREATED).body("Book added");
    }

    @GetMapping("/owned")
    public List<Book> ownedBooks(){
        UUID userid= UUID.randomUUID();
        return ownedBookService.ownedBooks(userid);
    }
    @PostMapping("/owned")
    public ResponseEntity<String> addOwnedBook(@RequestBody AddOwnedBookDTO dto) {
        ownedBookService.addOwnedBook(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Owned book added successfully");
    }
    @GetMapping("/wanted")
    public List<Book> getWantedBooks(){
        UUID userid= UUID.randomUUID();
        userid = UUID.fromString("99999999-0000-0000-0000-000000000001");
        return  wantedBookService.getWantedBooks(userid);
    }

    @PostMapping("/wanted")
    public ResponseEntity<String> addWantedBooks(@RequestBody AddWantedBookDTO dto){
        wantedBookService.addWantedBook(dto);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Wanted book added successfully");
    }


}
