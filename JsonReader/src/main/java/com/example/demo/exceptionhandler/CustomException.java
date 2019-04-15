package com.example.demo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = -5234697463903374589L;

	public CustomException(String message) {
		super(message);
	}

}
