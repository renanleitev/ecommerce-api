package com.amenity_reservation_system.controller;


import com.amenity_reservation_system.model.Product;
import com.amenity_reservation_system.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    public ResponseEntity<List<Product>> findProducts(
            @RequestParam(name = "_page", required = false) Integer page,
            @RequestParam(name = "_limit", required = false) Integer limit) {

        List<Product> productList;
        long totalProducts;
        int totalPages;

        if (page != null && limit != null) {
            Pageable pageable = PageRequest.of(page - 1, limit);
            Page<Product> productPage = productRepository.findAll(pageable);

            productList = productPage.getContent();
            totalProducts = productPage.getTotalElements();
            totalPages = productPage.getTotalPages();
        } else {
            productList = productRepository.findAll();
            totalProducts = productList.size();
            totalPages = 1;
        }

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", String.valueOf(totalProducts));
        responseHeaders.set("X-Total-Pages", String.valueOf(totalPages));

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // Retrieve the product from the database based on the provided ID
        Optional<Product> product = productRepository.findById(id);

        // Check if the product exists
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        // Retrieve the existing product from the database by its ID
        Optional<Product> existingProduct = productRepository.findById(id);

        if (existingProduct.isPresent()) {
            // Update the properties of the existing product with the new values
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            // Update other properties as needed

            // Save the updated product to the database
            Product savedProduct = productRepository.save(product);

            return ResponseEntity.ok(savedProduct);
        } else {
            // If the product with the given ID doesn't exist, return a not found status
            return ResponseEntity.notFound().build();
        }
    }

}
