package com.webdev.ws.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductErrorHandler {

	@ExceptionHandler(ProductServiceError.class)
	public ResponseEntity<String> handlerError(Exception e)
	{
		return new  ResponseEntity<>("Error occured"+e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
