package com.book.BookService.Security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;


import java.util.UUID;

public class SecurityUtil {

    public static UUID getCurrentUserId() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null) {
            throw new RuntimeException("Unauthenticated");
        }

        return UUID.fromString(auth.getPrincipal().toString());
    }
}
