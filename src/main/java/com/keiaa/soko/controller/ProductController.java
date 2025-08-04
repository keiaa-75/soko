package com.keiaa.soko.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.keiaa.soko.model.Product;
import com.keiaa.soko.repository.ProductRepository;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ProductController
 * 
 * This class exposes RESTful API endpoints for managing products in the inventory system.
 * It supports basic CRUD operations using Spring Data JPA and responds with JSON-formatted data.
 *
 * <p>Base path for all endpoints: <b>/api/products</b></p>
 *
 * <ul>
 *   <li><b>GET /api/products</b> - Returns a list of all products.</li>
 *   <li><b>GET /api/products/{id}</b> - Returns a single product by its ID.</li>
 *   <li><b>POST /api/products</b> - Creates a new product.</li>
 *   <li><b>PUT /api/products/{id}</b> - Updates an existing product by ID.</li>
 *   <li><b>DELETE /api/products/{id}</b> - Deletes a product by ID.</li>
 * </ul>
 *
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product geProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);        
        return productRepository.save(product);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam String name) {
        return productRepository.findByItemNameContainingIgnoreCase(name);
    }
    
    @GetMapping("/filter")
    public List<Product> filterProducts(
        @RequestParam(required = false) String category,
        @RequestParam(required = false) Double minPrice,
        @RequestParam(required = false) Double maxPrice
        ) {
        return productRepository.filterProducts(category, minPrice, maxPrice);
    }
    
    @GetMapping("/sort")
    public List<Product> sortProducts(@RequestParam String by, @RequestParam String order) {
        if ("price".equals(by)) {
            return "asc".equals(order) ? productRepository.findAllByOrderByItemPriceAsc() : productRepository.findAllByOrderByItemPriceDesc();
        }
        return productRepository.findAll();
    }
    
}
