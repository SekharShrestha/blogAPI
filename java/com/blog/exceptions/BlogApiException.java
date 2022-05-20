package com.blog.exceptions;

import org.springframework.http.HttpStatus;

public class BlogApiException extends Exception{

	private HttpStatus status;
	private String msg;
	
	public BlogApiException(HttpStatus status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public BlogApiException(String message, HttpStatus status, String msg) {
		super(message);
		this.status = status;
		this.msg = msg;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public String getMsg() {
		return msg;
	}
	
	
	
	
}
