package com.example.ntproject.service.book.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class BookAlreadyExists{

    public static ResponseStatusException create(long isbn) {
        return new ResponseStatusException(HttpStatus.CONFLICT, String.format("Book with isbn: %s already exists", isbn));
    }
}
