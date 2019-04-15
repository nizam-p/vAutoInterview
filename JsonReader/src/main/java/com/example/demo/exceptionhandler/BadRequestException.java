package com.example.demo.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 4500990704997429149L;

	public BadRequestException(String message) {
		super(message);
	}
	
	
}
