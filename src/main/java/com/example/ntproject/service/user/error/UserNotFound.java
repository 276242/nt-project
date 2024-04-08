package com.example.ntproject.service.user.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserNotFound {
    public static ResponseStatusException createById(long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User: %s (id) not found", id));
    }

    public static ResponseStatusException createByUsername(String username) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("User: %s (username) not found", username));
    }
}