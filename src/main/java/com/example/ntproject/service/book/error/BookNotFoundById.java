package com.example.ntproject.service.book.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookNotFoundById {
    public static ResponseStatusException create(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Book: %s (id) not found", id));
    }
}