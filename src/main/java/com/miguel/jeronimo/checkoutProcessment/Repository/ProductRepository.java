package com.miguel.jeronimo.checkoutProcessment.Repository;

import com.miguel.jeronimo.checkoutProcessment.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
