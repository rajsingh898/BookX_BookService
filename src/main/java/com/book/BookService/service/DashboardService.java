package com.book.BookService.service;

import com.book.BookService.dto.BookStatsDTO;
import com.book.BookService.repository.OwnedBookRepository;
import com.book.BookService.repository.WantedBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final OwnedBookRepository ownedBookRepository;
    private final WantedBookRepository wantedBookRepository;


    public BookStatsDTO getBookStats(UUID userId) {

        long owned = ownedBookRepository.countByUserId(userId);
        long read = ownedBookRepository.countByUserIdAndReadStatus(userId, "READ");
        long unread = ownedBookRepository.countByUserIdAndReadStatus(userId, "UNREAD");
        long wanted = wantedBookRepository.countByUserId(userId);

        return new BookStatsDTO(owned, read, unread, wanted);
    }
}
