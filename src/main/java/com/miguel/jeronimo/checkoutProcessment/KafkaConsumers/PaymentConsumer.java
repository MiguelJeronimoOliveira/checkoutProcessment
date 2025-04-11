package com.miguel.jeronimo.checkoutProcessment.KafkaConsumers;

import com.miguel.jeronimo.checkoutProcessment.Entities.Product;
import com.miguel.jeronimo.checkoutProcessment.Entities.ProductStatus;
import com.miguel.jeronimo.checkoutProcessment.Repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentConsumer {

    private final ProductRepository repository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public PaymentConsumer(ProductRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "newOrder-topic", groupId = "order-group")
    public void processPayment(Product product) {
        if(product.getPrice() > 0) {
            product.setStatus(ProductStatus.approved);
            repository.save(product);
            System.out.println("product status approved");
            kafkaTemplate.send("delivery-topic", product);
        }else{
            product.setStatus(ProductStatus.rejected);
            repository.save(product);
            System.out.println("product status rejected");
            kafkaTemplate.send("delivery-topic", product);
        }
    }
}
