package com.book.BookService.service;

import com.book.BookService.dto.BookSummaryDTO;
import com.book.BookService.dto.ExchangeMatchDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.entity.OwnedBook;
import com.book.BookService.entity.WantedBook;
import com.book.BookService.repository.OwnedBookRepository;
import com.book.BookService.repository.WantedBookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class MatchService {

    private final OwnedBookRepository ownedBookRepository;
    private final WantedBookRepository wantedBookRepository;

    public MatchService(OwnedBookRepository ownedBookRepository,
                        WantedBookRepository wantedBookRepository) {
        this.ownedBookRepository = ownedBookRepository;
        this.wantedBookRepository = wantedBookRepository;
    }

    public List<ExchangeMatchDTO> findMatches(UUID currentUserId) {

        List<Book> myOwned = ownedBookRepository.findBooksByUserId2(currentUserId);
        List<Book> myWanted = wantedBookRepository.findBooksByUserId(currentUserId);

        List<OwnedBook> candidates =
                ownedBookRepository.findByBookIn(myWanted);

        List<ExchangeMatchDTO> matches = new ArrayList<>();

        for (OwnedBook ob : candidates) {
            UUID otherUserId = ob.getUserId();

            if (otherUserId.equals(currentUserId)) continue;

            List<WantedBook> whatTheyWant =
                    wantedBookRepository.findByUserId(otherUserId);

            for (WantedBook wb : whatTheyWant) {
                if (myOwned.contains(wb.getBook())) {

                    ExchangeMatchDTO dto = new ExchangeMatchDTO();
                    dto.setOtherUserId(otherUserId);

                    dto.setTheyOffer(toSummary(ob.getBook()));
                    dto.setTheyWant(toSummary(wb.getBook()));

                    matches.add(dto);
                }
            }
        }

        return matches;
    }

    private BookSummaryDTO toSummary(Book book) {
        BookSummaryDTO dto = new BookSummaryDTO();
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        return dto;
    }
}
