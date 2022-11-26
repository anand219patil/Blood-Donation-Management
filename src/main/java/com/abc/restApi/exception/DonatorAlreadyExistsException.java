package com.abc.restApi.exception;

public class DonatorAlreadyExistsException extends RuntimeException{

	public DonatorAlreadyExistsException(String message) {
		
		super(message);
	}
}
