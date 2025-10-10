package com.webdev.ws.handler;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.webdev.ws.commands.PaymentProceedCommand;
import com.webdev.ws.commands.ProductReserveCommand;
import com.webdev.ws.errors.ProductServiceError;
import com.webdev.ws.model.ProductsEntity;
import com.webdev.ws.repository.ProductsRepository;


@Component
@KafkaListener(topics = "product-command", groupId = "product-group", containerFactory = "consumerListenerFactory")
public class ProductHandler {

	private ProductsRepository repository;
	private final static Logger logger = LoggerFactory.getLogger(ProductHandler.class);
	
	private KafkaTemplate<String,Object>kafkaTemplate;
	
	private String TOPIC_NAME;
	ProductHandler(ProductsRepository repository,
			KafkaTemplate<String,Object>kafkaTemplate,
			@Value("${product.created.topic.name}") String TOPIC_NAME)
	{
		this.repository = repository;
		this.kafkaTemplate = kafkaTemplate;
		this.TOPIC_NAME = TOPIC_NAME;
	}
	
	
	@KafkaHandler
	public void handler(@Payload ProductReserveCommand reserve) throws Exception {
		try {
			
			ProductsEntity entity = repository.findByProductId(reserve.getProductId());
			if (entity == null) {
				throw new ProductServiceError("Product not found with id" + reserve.getProductId());
			}

			if (entity.getQuantity() < reserve.getQuantity()) {
				logger.error("Order quantity exceeded. Available quantity" + entity.getQuantity());
				throw new ProductServiceError("Order quantity exceeds");
			}

			int quantity = entity.getQuantity() - reserve.getQuantity();
			entity.setQuantity(quantity);
			PaymentProceedCommand command = new PaymentProceedCommand(entity.getProductId(),reserve.getOrderId(),
					reserve.getQuantity(),entity.getPrice());

			ProducerRecord<String, Object> records = new ProducerRecord(TOPIC_NAME, entity.getProductId().toString(), command);
			SendResult<String, Object> result = kafkaTemplate.send(records).get();
			repository.save(entity);
			logger.info("Topic partition ", result.getRecordMetadata().partition());
			logger.info("Topic name" + result.getRecordMetadata().topic());
		} catch (Exception e) {
			logger.error("error while retriving data from DB" + e.getLocalizedMessage());
			throw new Exception(e.getLocalizedMessage());
		}
	}
	

@KafkaHandler(isDefault = true)
    public void handleUnknown(Object unknown) {
        System.out.println("Unknown type: " + unknown.getClass());
    }	
}
