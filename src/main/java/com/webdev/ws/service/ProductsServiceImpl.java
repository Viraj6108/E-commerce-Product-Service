package com.webdev.ws.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.webdev.ws.dto.ProductsDTO;
import com.webdev.ws.model.ProductsEntity;
import com.webdev.ws.repository.ProductsRepository;

@Component
public class ProductsServiceImpl implements ProductService {

	private ProductsRepository productsRepository;
	
	public ProductsServiceImpl(ProductsRepository productsRepository)
	{
		this.productsRepository=productsRepository;
	}
	
	@Override
	public ProductsEntity createProduct(ProductsDTO productsDTO) {
		
		//Validate product if existing then update the quantity 
		ProductsEntity entity = productsRepository.findByProductName(productsDTO.getProductName());
		if (entity==(null)) {
			entity= new ProductsEntity();
			entity.setPrice(productsDTO.getPrice());
			entity.setProductName(productsDTO.getProductName());
			entity.setProductId(UUID.randomUUID());
			entity.setQuantity(productsDTO.getQuantity());
			
		}else {
			entity.setQuantity(productsDTO.getQuantity());
			entity.setPrice(productsDTO.getPrice());
			
		}
		productsRepository.save(entity);
		return entity;
	}

	@Override
	public List<ProductsEntity> getProducts() {
		// TODO Auto-generated method stub
	List<ProductsEntity>listOfProdcuts=	productsRepository.findAll();
		return listOfProdcuts;
	}

	
}
