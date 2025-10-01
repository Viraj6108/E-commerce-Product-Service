package com.webdev.ws.service;

import java.util.List;

import com.webdev.ws.dto.ProductsDTO;
import com.webdev.ws.model.ProductsEntity;

public interface ProductService {

	ProductsEntity createProduct(ProductsDTO productsDTO);
	
	List<ProductsEntity> getProducts();
}
