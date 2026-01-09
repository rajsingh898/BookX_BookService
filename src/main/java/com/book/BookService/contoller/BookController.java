package com.book.BookService.contoller;

import com.book.BookService.Security.SecurityUtil;
import com.book.BookService.dto.*;
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
    public List<MyOwnedBookDto> ownedBooks(){
        UUID userId = SecurityUtil.getCurrentUserId();
        return ownedBookService.ownedBooks(userId);
     }
    @PostMapping("/owned")
    public ResponseEntity<String> addOwnedBook(@RequestBody AddOwnedBookDTO dto) {
        UUID userId = SecurityUtil.getCurrentUserId();
        ownedBookService.addOwnedBook(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Owned book added successfully");
    }
    @DeleteMapping("/owned")
    public ResponseEntity<String> removeOwnedBooks(@RequestParam UUID bookId){
        UUID userId = SecurityUtil.getCurrentUserId();
        ownedBookService.removeOwnedBook(bookId, userId);
        return  ResponseEntity.status(HttpStatus.OK).body("Owned book removed successfully");
    }



    @PatchMapping("/update")
  public MyOwnedBookDto  updateOwnedBook(@RequestParam  UUID bookId,@RequestBody UpdateOwnedBookRequest request) {
                UUID userId = SecurityUtil.getCurrentUserId();
               return ownedBookService.updateOwnedBook(bookId, userId, request);
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
    @DeleteMapping("/wanted")
    public ResponseEntity<String> removeWantedBooks(@RequestParam UUID bookId){
        UUID userId = SecurityUtil.getCurrentUserId();
        wantedBookService.removeWantedBook(bookId, userId);
        return  ResponseEntity.status(HttpStatus.OK).body("Wanted book removed successfully");
    }
    @GetMapping("/exchange/matches")
    public void matched(){
        UUID currentUserId = SecurityUtil.getCurrentUserId();
          matchService.findMatches(currentUserId);
    }


}
