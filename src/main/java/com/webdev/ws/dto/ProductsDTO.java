package com.webdev.ws.dto;

import java.util.UUID;



public class ProductsDTO {

	
	private String productName;
	private double price;
	private Integer quantity;
	public ProductsDTO() {
		super();
	}
	public ProductsDTO(String productName, double price, Integer quantity) {
		super();
		
		this.productName = productName;
		this.price = price;
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
}
