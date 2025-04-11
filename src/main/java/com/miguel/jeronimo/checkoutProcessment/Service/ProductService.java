package com.miguel.jeronimo.checkoutProcessment.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miguel.jeronimo.checkoutProcessment.Entities.Product;
import com.miguel.jeronimo.checkoutProcessment.Repository.ProductRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

   private final ProductRepository repository;

   private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProductService(ProductRepository repository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void addProduct(Product product) {

        repository.save(product);
        kafkaTemplate.send("newOrder-topic", product);

    }

    public List<Product> getProducts(){
        return repository.findAll();
    }

    public void deleteProduct(Long id){
        repository.deleteById(id);
    }

    public void updateProduct(Product newProduct, Long id){
        Optional<Product> product = repository.findById(id);

        if(product.isPresent()){
            product.get().setName(newProduct.getName());
            product.get().setPrice(newProduct.getPrice());
            repository.save(product.get());
        }
    }

    public Product findById(Long id){
        Optional<Product> product = repository.findById(id);

        return product.orElse(null);

    }
}
