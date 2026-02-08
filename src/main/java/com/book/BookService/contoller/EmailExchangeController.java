package com.book.BookService.contoller;

import com.book.BookService.dto.ExchangeEmailRequestDTO;
import com.book.BookService.service.ExchangeEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class EmailExchangeController {

    private final ExchangeEmailService exchangeEmailService;

    @PostMapping("/send-email")
    public void sendExchangeEmail(
            @RequestBody ExchangeEmailRequestDTO dto
    ) {
        exchangeEmailService.send(dto);
    }


}
