package com.myblog.myblog.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
	public NotFoundException() {
		
	}
	public NotFoundException(String messaString) {
		super(messaString);
	}
	
	
	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
	

}
