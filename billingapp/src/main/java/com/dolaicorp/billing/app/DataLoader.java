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
                new Product("Coca-Cola", "8901764012303", 50.00),
                new Product("FeviKwik", "8901860010005", 5.00),
                new Product("Nivea Soft", "42241782", 55.00)
        );
        productRepository.saveAll(products);
    }
}