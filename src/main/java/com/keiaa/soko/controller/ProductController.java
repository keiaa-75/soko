package com.keiaa.soko.controller;

import java.util.List;

import com.keiaa.soko.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.keiaa.soko.model.Product;


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
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder) {
        return productService.findProducts(name, category, minPrice, maxPrice, sortBy, sortOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }
    
    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/categories")
    public List<String> getCategories() {
        return productService.getAllCategories();
    }
    
}
