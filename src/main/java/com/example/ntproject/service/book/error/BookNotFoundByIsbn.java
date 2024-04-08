package com.example.ntproject.service.book.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundByIsbn {
    public static ResponseStatusException create(String isbn) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book: %s (isbn) not found", isbn));
    }
}
