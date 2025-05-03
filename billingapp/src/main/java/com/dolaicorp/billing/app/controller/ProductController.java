package com.dolaicorp.billing.app.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dolaicorp.billing.app.model.Product;
import com.dolaicorp.billing.app.repository.ProductRepository;

@Controller
@RequestMapping("/")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/billing")
    public String billingPage(Model model) {
        return "billing";
    }

    @GetMapping("/api/products/{barcode}")
    @ResponseBody
    public ResponseEntity<Product> getProductByBarcode(@PathVariable String barcode) {
        Optional<Product> productOptional = productRepository.findByBarcode(barcode);
        if (productOptional.isPresent()) {
            return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}