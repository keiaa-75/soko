package com.keiaa.soko.service;

import com.keiaa.soko.model.Product;
import com.keiaa.soko.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findProducts(String name, String category, Double minPrice, Double maxPrice, String sortBy, String sortOrder) {
        // Use Specification for dynamic query building
        Specification<Product> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("itemName")), "%" + name.toLowerCase() + "%"));
            }
            if (category != null && !category.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.get("itemCategory"), category));
            }
            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("itemPrice"), minPrice));
            }
            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("itemPrice"), maxPrice));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        // Define sort order, mapping frontend values to backend fields
        String sortField;
        if (sortBy != null) {
            switch (sortBy.toLowerCase()) {
                case "name":
                    sortField = "itemName";
                    break;
                case "price":
                    sortField = "itemPrice";
                    break;
                case "quantity":
                    sortField = "itemQty";
                    break;
                case "id":
                default:
                    sortField = "id";
                    break;
            }
        } else {
            sortField = "id";
        }
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOrder) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);

        return productRepository.findAll(spec, sort);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        productDetails.setId(id);
        return productRepository.save(productDetails);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<String> getAllCategories() {
        return productRepository.findDistinctItemCategories();
    }
}