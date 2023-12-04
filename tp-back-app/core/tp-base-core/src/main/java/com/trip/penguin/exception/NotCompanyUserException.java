package com.trip.penguin.exception;

public class NotCompanyUserException extends RuntimeException{

    private static final String message = "NOT COMPANY USER";

    public NotCompanyUserException() {
        super(message);
    }

    public NotCompanyUserException(String message) {
        super(message);
    }
}
