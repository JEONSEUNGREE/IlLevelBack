package com.trip.penguin.exception;

public class NotDefaultUserException extends RuntimeException{

    private static final String message = "NOT DEFAULT USER";

    public NotDefaultUserException() {
        super(message);
    }

    public NotDefaultUserException(String message) {
        super(message);
    }
}
