package com.example.ntproject.service.loan.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LoanNotFound {
    public static ResponseStatusException create(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Loan: %s (id) not found", id));
    }

    public static ResponseStatusException createByUserId(long userId) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Loan for user: %s (id) not found", userId));
    }
}
