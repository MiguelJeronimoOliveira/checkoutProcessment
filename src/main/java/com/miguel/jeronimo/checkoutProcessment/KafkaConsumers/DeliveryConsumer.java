package com.miguel.jeronimo.checkoutProcessment.KafkaConsumers;

import com.miguel.jeronimo.checkoutProcessment.Entities.Product;
import com.miguel.jeronimo.checkoutProcessment.Entities.ProductStatus;
import com.miguel.jeronimo.checkoutProcessment.Repository.ProductRepository;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DeliveryConsumer {

    private final ProductRepository repository;

    public DeliveryConsumer(ProductRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "delivery-topic", groupId = "order-group")
    public void readyForDelivery(Product product) {
        if(product.getStatus() == ProductStatus.approved) {
            product.setToDelivery(true);
            repository.save(product);
            System.out.println("The Product is ready for delivery");
        }else {
            System.out.println("The Product is rejected for delivery");
        }
    }
}
