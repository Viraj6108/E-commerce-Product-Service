package com.webdev.ws.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.webdev.ws.commands.ProductReserveCommand;
import com.webdev.ws.errors.ProductServiceError;
import com.webdev.ws.model.ProductsEntity;
import com.webdev.ws.repository.ProductsRepository;

@KafkaListener(topics = "product-reserve-command")
public class ProductHandler {

	private ProductsRepository repository;
	private final static Logger logger = LoggerFactory.getLogger(ProductHandler.class);
	ProductHandler(ProductsRepository repository)
	{
		this.repository = repository;
	}
	
	
	public void handler(ProductReserveCommand reserve) throws Exception
	{
		try {
		ProductsEntity entity = repository.findByProductId(reserve.getProductId());
		if(entity==null)
		{
			throw new ProductServiceError("Product not found with id"+reserve.getProductId());
		}
		
		if(entity.getQuantity()<= reserve.getQuantity())
		{
			logger.error("Order quantity exceeded. Available quantity"+entity.getQuantity());
			throw new ProductServiceError("Order quantity exceeds");
		}
		
		int quantity = entity.getQuantity()-reserve.getQuantity();
		entity.setQuantity(quantity);
		repository.save(entity);
		}catch(Exception e)
		{
			logger.error("error while retriving data from DB"+e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
	}
}
