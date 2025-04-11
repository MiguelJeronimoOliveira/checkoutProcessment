package com.miguel.jeronimo.checkoutProcessment.Controller;

import com.miguel.jeronimo.checkoutProcessment.Entities.Product;
import com.miguel.jeronimo.checkoutProcessment.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity postProduct(@RequestBody Product product) {
        service.addProduct(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity getProducts() {

        if(service.getProducts().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(service.getProducts());
    }

    @PutMapping("{id}")
    public ResponseEntity putProduct(@PathVariable Long id, @RequestBody Product product) {

        if(id == null){
            return ResponseEntity.badRequest().build();
        }

        service.updateProduct(product, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {

        Optional<Product> product = Optional.ofNullable(service.findById(id));

        if(product.isPresent()) {
            service.deleteProduct(id);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
