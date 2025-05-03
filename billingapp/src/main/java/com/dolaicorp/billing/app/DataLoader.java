package com.dolaicorp.billing.app;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dolaicorp.billing.app.model.Product;
import com.dolaicorp.billing.app.repository.ProductRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataLoader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Product> products = List.of(
                new Product("Laptop", "1234567890", 1200.00),
                new Product("Mouse", "0987654321", 25.00),
                new Product("Keyboard", "1122334455", 75.00)
        );
        productRepository.saveAll(products);
    }
}