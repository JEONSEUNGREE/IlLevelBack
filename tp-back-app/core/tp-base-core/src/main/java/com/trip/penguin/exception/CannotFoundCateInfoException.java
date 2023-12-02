package com.trip.penguin.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CannotFoundCateInfoException extends RuntimeException {

    private static final String message = "CannotFoundCateInfoException";

    public CannotFoundCateInfoException() {
        super(message);
    }

    public CannotFoundCateInfoException(String message) {
        super(message);
    }
}
