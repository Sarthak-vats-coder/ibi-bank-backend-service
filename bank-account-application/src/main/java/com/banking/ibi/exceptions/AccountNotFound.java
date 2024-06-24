package com.banking.ibi.exceptions;

public class AccountNotFound extends Exception {

	private static final long serialVersionUID = -7863357966535294073L;

	public AccountNotFound(String message) {
		super(message);
	}

}
