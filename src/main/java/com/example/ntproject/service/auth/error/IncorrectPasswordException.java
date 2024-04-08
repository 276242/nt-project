package com.example.ntproject.service.auth.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectPasswordException {
    public static ResponseStatusException create(){
        return new ResponseStatusException(HttpStatus.CONFLICT, "Incorrect password");
    }
}
