package com.abc.restApi.exception;

import java.util.Date;

public class ErrorResponse {

	private String message;
	private Date date;

	public ErrorResponse() {
		
	}

	public ErrorResponse(String message, Date date) {
		super();
		this.message = message;
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
