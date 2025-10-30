package com.webdev.ws.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webdev.ws.model.ProductsEntity;

@Repository
public interface ProductsRepository extends JpaRepository<ProductsEntity,Long>{

	ProductsEntity findByProductName(String productName);
	Optional<ProductsEntity> findByProductId(UUID productId);
}
