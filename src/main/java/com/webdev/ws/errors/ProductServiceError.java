package com.webdev.ws.errors;

public class ProductServiceError extends Exception{

	public ProductServiceError(String error)
	{
		super(error);
	}
}
