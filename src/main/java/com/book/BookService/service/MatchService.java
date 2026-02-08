package com.book.BookService.service;

import com.book.BookService.dto.BookSummaryDTO;
import com.book.BookService.dto.ExchangeMatchDTO;
import com.book.BookService.dto.ExchangeOfferDTO;
import com.book.BookService.entity.Book;
import com.book.BookService.entity.OwnedBook;
import com.book.BookService.entity.WantedBook;
import com.book.BookService.repository.OwnedBookRepository;
import com.book.BookService.repository.WantedBookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class MatchService {

    private final OwnedBookRepository ownedBookRepository;
    private final WantedBookRepository wantedBookRepository;

    public MatchService(OwnedBookRepository ownedBookRepository,
                        WantedBookRepository wantedBookRepository) {
        this.ownedBookRepository = ownedBookRepository;
        this.wantedBookRepository = wantedBookRepository;
    }

    public List<ExchangeOfferDTO> findMatchesForOwnedBook(
            UUID currentUserId,
            UUID bookId
    ) {

        // Only allow exchange-ready owned book
        OwnedBook myOwnedBook =
                ownedBookRepository
                        .findByBook_IdAndUserIdAndExchangeReadyTrue(bookId, currentUserId)
                        .orElseThrow(() -> new RuntimeException("Owned book not found or not ready for exchange"));

        Book bookIAmOffering = myOwnedBook.getBook();

        // My wanted list
        List<Book> myWanted =
                wantedBookRepository.findBooksByUserId(currentUserId);

        var wantedBookIds = myWanted.stream()
                .map(Book::getId)
                .collect(java.util.stream.Collectors.toSet());

        // Users who want my book
        List<WantedBook> usersWhoWantMyBook =
                wantedBookRepository.findByBook_Id(bookIAmOffering.getId());

        List<ExchangeOfferDTO> offers = new ArrayList<>();

        for (WantedBook wb : usersWhoWantMyBook) {

            UUID otherUserId = wb.getUserId();
            if (otherUserId.equals(currentUserId)) continue;

            //  Only fetch books ready for exchange
            List<OwnedBook> theirOwnedBooks =
                    ownedBookRepository.findByUserIdAndExchangeReadyTrue(otherUserId);

            for (OwnedBook theirOwned : theirOwnedBooks) {

                if (wantedBookIds.contains(theirOwned.getBook().getId())) {

                    ExchangeOfferDTO dto = new ExchangeOfferDTO();
                    dto.setOtherUserId(otherUserId);

                    dto.setMyBook(toSummary(bookIAmOffering));
                    dto.setTheirBook(toSummary(theirOwned.getBook()));
                    dto.setTheirBookCondition(theirOwned.getCondition());

                    offers.add(dto);
                    //show only max 5 matches
                    if (offers.size() >= 5) {
                        return offers;
                    }
                }
            }
        }

        return offers;
    }



    private BookSummaryDTO toSummary(Book book) {
        BookSummaryDTO dto = new BookSummaryDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setGenre(book.getGenre());
        dto.setDescription(book.getDescription());
        return dto;
    }

}
