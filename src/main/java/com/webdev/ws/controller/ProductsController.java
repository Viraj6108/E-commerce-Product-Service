package com.webdev.ws.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webdev.ws.dto.ProductsDTO;
import com.webdev.ws.model.ProductsEntity;
import com.webdev.ws.service.ProductService;


@RestController
@RequestMapping("/products")
public class ProductsController {

	private ProductService service;
	
	public ProductsController(ProductService service)
	{
		this.service = service;
	}
	
	@PostMapping("/post")
	public ProductsEntity createProduct(@RequestBody ProductsDTO dto)
	{
		
		return service.createProduct(dto);
	}
	
	@GetMapping("/get")
	public List<ProductsEntity> getAllProducts()
	{
		return service.getProducts();
	}
}
