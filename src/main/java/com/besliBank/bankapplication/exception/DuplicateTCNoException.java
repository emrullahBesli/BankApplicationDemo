package com.besliBank.bankapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicateTCNoException extends RuntimeException{
    public DuplicateTCNoException(String message) {
        super(message);
    }
}

