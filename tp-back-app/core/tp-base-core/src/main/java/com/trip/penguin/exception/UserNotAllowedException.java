package com.trip.penguin.exception;

public class UserNotAllowedException extends RuntimeException {

	private static final String message = "UserNotAllowedException";

	public UserNotAllowedException() {
		super(message);
	}

	public UserNotAllowedException(String message) {
		super(message);
	}
}
