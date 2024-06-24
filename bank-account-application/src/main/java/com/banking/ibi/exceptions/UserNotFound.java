package com.banking.ibi.exceptions;

public class UserNotFound extends Exception {

	private static final long serialVersionUID = 7707426671948807190L;

	public UserNotFound(String message) {
		super(message);
	}
}
