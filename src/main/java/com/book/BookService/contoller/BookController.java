package com.book.BookService.contoller;

import com.book.BookService.Security.SecurityUtil;
import com.book.BookService.dto.AddOwnedBookDTO;
import com.book.BookService.dto.AddWantedBookDTO;
import com.book.BookService.dto.RequestBookDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.service.BookService;
import com.book.BookService.service.MatchService;
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
    private MatchService matchService;
    public BookController(BookService bookService,OwnedBookService ownedBookService,WantedBookService wantedBookService, MatchService matchService){
        this.bookService= bookService;
        this.ownedBookService = ownedBookService;
        this.wantedBookService=wantedBookService;
        this.matchService = matchService;
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
        UUID userId = SecurityUtil.getCurrentUserId();
        return ownedBookService.ownedBooks(userId);
     }
    @PostMapping("/owned")
    public ResponseEntity<String> addOwnedBook(@RequestBody AddOwnedBookDTO dto) {
        UUID userId = SecurityUtil.getCurrentUserId();
        ownedBookService.addOwnedBook(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Owned book added successfully");
    }
    @GetMapping("/wanted")
    public List<Book> getWantedBooks(){
        UUID userId = SecurityUtil.getCurrentUserId();
        return  wantedBookService.getWantedBooks(userId);
    }

    @PostMapping("/wanted")
    public ResponseEntity<String> addWantedBooks(@RequestBody AddWantedBookDTO dto){
        UUID userId = SecurityUtil.getCurrentUserId();
        wantedBookService.addWantedBook(dto, userId);
        return  ResponseEntity.status(HttpStatus.CREATED).body("Wanted book added successfully");
    }
    @GetMapping("/exchange/matches")
    public void matched(){
        UUID currentUserId = SecurityUtil.getCurrentUserId();
          matchService.findMatches(currentUserId);
    }


}
